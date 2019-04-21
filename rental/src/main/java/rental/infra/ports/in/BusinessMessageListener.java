package rental.infra.ports.in;

public interface BusinessMessageListener {
    void onMessage(Object message);
}
