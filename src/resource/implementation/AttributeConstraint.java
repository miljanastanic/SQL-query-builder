package resource.implementation;

import resource.DBNode;
import resource.enums.ConstraintType;

public class AttributeConstraint extends DBNode {

        private ConstraintType constraintType;

        public AttributeConstraint(String name, DBNode parent, ConstraintType type){
            super(name, parent);
            this.constraintType = type;

        }

    public ConstraintType getConstraintType() {
        return constraintType;
    }

    public void setConstraintType(ConstraintType constraintType) {
        this.constraintType = constraintType;
    }
}
