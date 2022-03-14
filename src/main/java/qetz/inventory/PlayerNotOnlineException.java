package qetz.inventory;

public final class PlayerNotOnlineException extends RuntimeException {
  public static PlayerNotOnlineException create() {
    return new PlayerNotOnlineException();
  }

  private static final String message = "player to interact with is not online";

  private PlayerNotOnlineException() {
    super(message);
  }
}