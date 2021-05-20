package errorHandler;

import observer.Publisher;

public interface ErrorHandler extends Publisher {
    void generateError(Type type);
}
