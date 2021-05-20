package observer;

public interface Publisher {

    void addSubsriber(Subscriber subscriber);
    void notifySubscriber(Notification notification);
    void removeSubscriber(Subscriber subscriber);
}
