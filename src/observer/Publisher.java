package observer;

public interface Publisher {

    void addSubsriber(Subscriber subscriber);
    void notifySubscriber(Subscriber subscriber);
    void removeSubscriber(Notification notification);
}
