package database.repositories;

import database.Database;
import database.settings.Settings;
import resource.DBNode;
import resource.data.Row;
import resource.enums.AttributeType;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import resource.implementation.InformationResource;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MSSQLrepository implements Repository{

    private Settings settings;
    private Connection connection;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

    public MSSQLrepository(Settings settings){
        this.settings = settings;
    }
    private void initialiseConnection() throws SQLException, ClassNotFoundException {
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        String ip = (String) settings.getParameter("mssql_ip");
        String database = (String) settings.getParameter("mssql_database");
        String username = (String) settings.getParameter("mssql_username");
        String password = (String) settings.getParameter("mssql_password");
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:jtds:sqlserver://"+ip+"/"+database,username,password);
    }
    private void closeConnection(){
        try {
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            connection = null;
        }
    }

    @Override
    public DBNode getSchema() {
        //try {
//            this.initialiseConnection();
//
//            DatabaseMetaData metaData = connection.getMetaData();
//            InformationResource ir = new InformationResource("BP_Projekat");
//
//            String tabletype[] = {"TABLE"};
//            ResultSet tables = metaData.getTables(connection.getCatalog(),null,null,tabletype);
//
//            while(tables.next()){
//                String tableName = tables.getString("TABLE_NAME");
//                Entity newTable = new Entity(tableName, ir);
//                ir.addChild(newTable);
//
//                ResultSet columns = metaData.getColumns(connection.getCatalog(),null,tableName,null);
//
//                while (columns.next()){
//                    String columnName = columns.getString("COLUMN_NAME");
//                    String columnType = columns.getString("TYPE_NAME");
//                    int columnSize = Integer.parseInt(columns.getString("COLUMN_SIZE"));
//                    Attribute attribute = new Attribute(columnName,newTable, AttributeType.valueOf(columnType.toUpperCase()),columnSize);
//                    newTable.addChild(attribute);
//                }
//            }
//            return ir;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        finally {
//            this.closeConnection();
//        }
        return null;
    }

    @Override
    public List<Row> get(String from) {

        List<Row>rows = new ArrayList<>();
        try {
            initialiseConnection();

            String query = from;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println(rs + "resultset");

            while (rs.next()){
                Row row = new Row();
                row.setName(from);
                //System.out.println("aaaa");
                ResultSetMetaData resultSetMetaData = rs.getMetaData();
//
                for(int i = 1; i < resultSetMetaData.getColumnCount()+1; i++){
//                    for (Object o: row.getFields().values()) {
//                        if (o instanceof String){
//                            row.addField(resultSetMetaData.getColumnName(i),rs.getString(i));
//                        }else if(o instanceof Integer){
//                            row.addField(resultSetMetaData.getColumnName(i),rs.getInt(i));
//                        }else if(o instanceof Date){
//                            row.addField(resultSetMetaData.getColumnName(i),rs.getDate(i));
//                        }else {
//                            row.addField(resultSetMetaData.getColumnName(i),rs.getString(i));
//                        }
//                    }
                    int tip = resultSetMetaData.getColumnType(i);
                    if (tip == Types.VARCHAR || tip == Types.CHAR) {
                        if(resultSetMetaData.getColumnName(i).equalsIgnoreCase("hire_date")){
                        String dateString = rs.getString(i);
                        Date date = sdf.parse(dateString);
                        String date1 = simpleDateFormat.format(date);
                        row.addField(resultSetMetaData.getColumnName(i), date1);
                    } else
                        row.addField(resultSetMetaData.getColumnName(i),rs.getString(i));
                    }else if(tip == Types.DATE){
                        row.addField(resultSetMetaData.getColumnName(i),rs.getDate(i));
                    }else if(tip == Types.DOUBLE){
                        row.addField(resultSetMetaData.getColumnName(i),rs.getDouble(i));
                    }else if(tip == Types.TIMESTAMP) {
                        row.addField(resultSetMetaData.getColumnName(i),simpleDateFormat.format(rs.getTimestamp(i)));
                    }else if(tip == Types.INTEGER){
                        row.addField(resultSetMetaData.getColumnName(i),rs.getInt(i));
                    }else
                        row.addField(resultSetMetaData.getColumnName(i),rs.getObject(i));
                    //row.addField(resultSetMetaData.getColumnName(i),rs.getString(i));
                }
                rows.add(row);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            this.closeConnection();
        }
        return rows;
    }
}
