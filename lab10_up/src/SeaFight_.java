import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SeaFight_ extends Remote {
    int playerRegister(String nickname, Client_ client) throws RemoteException;
    void playerUnregister(String nickname) throws RemoteException;
    boolean addShipToPlayer(String nickname, Ship_ ship) throws RemoteException;
    void deletePlayersShip(String nickname, Ship_ ship) throws RemoteException;
    boolean changeShipPlace(String nickname, Ship_ oldShip, int newX, int newY) throws RemoteException;
    int setEnemyNickname(String nickname, String enemyNickname) throws RemoteException;
    int getShipsCount(String nickname) throws RemoteException;
    void call(String nick, String enemyNick, SeaFight_ serv) throws RemoteException;
    void setInfo(String nick, String text) throws RemoteException;
    String getInfo(String nick) throws RemoteException;
    void setGo(String nick, boolean value) throws RemoteException;
    boolean getGo(String nick) throws RemoteException;
    void setBlock(String nick, boolean value) throws RemoteException;
    boolean getBlock(String nick) throws RemoteException;
    void setPlay(String nick, boolean value) throws RemoteException;
    boolean getPlay(String nick) throws RemoteException;
    void setReset(String nick, boolean value) throws RemoteException;
    boolean getReset(String nick) throws RemoteException;
    boolean getWin(String nick) throws RemoteException;
    boolean getGameOver(String nick) throws RemoteException;
    int[][] getGrid(String nick) throws RemoteException;
    int[][] getEnemyGrid(String nick) throws RemoteException;
    void attack(String nick, int x, int y) throws RemoteException;
    String getEnemyNick(String nick) throws RemoteException;
    void resetMeAndEnemy(String nick) throws RemoteException;
}