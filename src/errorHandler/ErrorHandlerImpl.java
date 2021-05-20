package errorHandler;

import observer.Notification;
import observer.NotificationCode;
import observer.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandlerImpl implements ErrorHandler{


    List<Subscriber>subscribers;
    @Override
    public void generateError(Type type) {
        if(type == Type.CANNOT_COMPILE){
            this.notifySubscriber(new Notification(NotificationCode.CANNOT_COMP,new Error(1,"cannot compile","cannot compile")));
        }
    }

    @Override
    public void addSubsriber(Subscriber subscriber) {
        if (subscribers == null) {
            subscribers = new ArrayList<Subscriber>();
        }
        if (!subscribers.contains(subscriber)) {
            subscribers.add(subscriber);
        }
    }

    @Override
    public void notifySubscriber(Notification notification) {
        if (subscribers == null || subscribers.isEmpty() || notification == null) {
            return;
        }
        for (Subscriber subscriber : subscribers) {
            subscriber.update(notification);
        }
    }


    @Override
    public void removeSubscriber(Subscriber subscriber) {
        if(subscriber!=null){
            subscribers.remove(subscriber);
        }
    }
}
