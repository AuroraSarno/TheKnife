package ClientTK;

public class ConnessioneRMI {
    private static TheKnifeInterface oggettoRemoto;
    public static void setServer(TheKnifeInterface stub) {
        oggettoRemoto = stub;
    }
    public static TheKnifeInterface getServer() {
        return oggettoRemoto;
    }
}
