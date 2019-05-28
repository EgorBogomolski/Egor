import javax.swing.*;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class Server_ implements SeaFight_{

    private Vector<Player_> players = new Vector<>();

    public static void main(String[] args) throws IOException {
        if(args.length != 1) {
            System.out.println("Недопустимое число аргументов!");
            System.in.read();
            System.exit(0);
        }
        Server_ service = new Server_();

        try {
            LocateRegistry.createRegistry(Integer.parseInt(args[0]));
            Registry registry = LocateRegistry.getRegistry(Integer.parseInt(args[0]));
            Remote stub = UnicastRemoteObject.exportObject(service, 0);
            registry.bind("ClientRegister", stub);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        System.out.println("Server started.");

        try{
            Thread.sleep(Integer.MAX_VALUE);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int playerRegister(String nickname, Client_ client) {
        if(nickname.isEmpty()) return -1;
        for(Player_ pl : players)
            if(nickname.equals(pl.getNickname())) return -2;
        Player_ pl = new Player_(nickname, client);
        players.add(pl);
        System.out.println("Игрок " + nickname + " подключён к серверу.");
        return 1;
    }

    @Override
    public void playerUnregister(String nickname) {
        for(int i = 0; i < players.size(); i++)
            if(nickname.equals(players.elementAt(i).getNickname())) {
                players.remove(i);
                System.out.println("Игрок " + nickname + " отключён от сервера.");
                return;
            }
    }

    @Override
    public boolean addShipToPlayer(String nickname, Ship_ ship){
        for(Player_ pl : players){
            if(pl.getNickname().equals(nickname)){
                return pl.addShip(ship.getKoords()[0].x, ship.getKoords()[0].y, ship.getHor_vert(), ship.getLength());
            }
        }
        return false;
    }

    @Override
    public void deletePlayersShip(String nickname, Ship_ ship) {
        for(Player_ pl : players){
            if(pl.getNickname().equals(nickname)){
                pl.deleteShip(ship.getKoords()[0].x - 1, ship.getKoords()[0].y - 1, ship.getHor_vert(),
                        ship.getLength());
                return;
            }
        }
    }

    @Override
    public boolean changeShipPlace(String nickname, Ship_ oldShip, int newX, int newY) {
        for(Player_ pl : players){
            if(pl.getNickname().equals(nickname)){
                return pl.changeShipPlace(oldShip.getKoords()[0].x - 1, oldShip.getKoords()[0].y - 1, newX, newY,
                        oldShip.getHor_vert(), oldShip.getLength());
            }
        }
        return false;
    }

    @Override
    public int setEnemyNickname(String nickname, String enemyNickname) {
        if(enemyNickname.equals("NOTHING")){
            for (Player_ pl : players)
                if (pl.getNickname().equals(nickname)) {
                    pl.setEnemyNickname(enemyNickname);
                    return 1;
                }
        }

        if(enemyNickname.isEmpty()) return -1;
        boolean temp = false;
        for (Player_ pl : players)
            if (pl.getNickname().equals(enemyNickname)) {
                temp = true;
                break;
            }
        if(!temp) return -2;
        if(nickname.equals(enemyNickname)) return -3;

        for (Player_ pl : players)
            if (pl.getNickname().equals(nickname)) {
                pl.setEnemyNickname(enemyNickname);
                return 1;
            }

        return 0;
    }

    @Override
    public int getShipsCount(String nickname) {
        for(Player_ pl : players){
            if(pl.getNickname().equals(nickname))
                return pl.getShipsCount();
        }
        return 0;
    }

    @Override
    public void call(String nick, String enemyNick, SeaFight_ serv) {
        for(Player_ pl : players) {
            if (pl.getNickname().equals(enemyNick)) {
                JFrame fr = pl.client.createAskFrame(nick, enemyNick, serv);
                fr.setVisible(true);
                return;
            }
        }
    }

    @Override
    public void setInfo(String nick, String text) {
        for(Player_ pl : players){
            if(pl.getNickname().equals(nick)){
                pl.setInfo(text);
                return;
            }
        }
    }

    @Override
    public String getInfo(String nick) {
        for(Player_ pl : players){
            if(pl.getNickname().equals(nick)){
                return pl.getInfo();
            }
        }
        return "";
    }

    @Override
    public void setGo(String nick, boolean value) {
        for(Player_ pl : players){
            if(pl.getNickname().equals(nick)){
                pl.setGo(value);
                return;
            }
        }
    }

    @Override
    public boolean getGo(String nick) {
        for(Player_ pl : players){
            if(pl.getNickname().equals(nick)){
                return pl.getGo();
            }
        }
        return false;
    }

    @Override
    public void setBlock(String nick, boolean value) {
        for(Player_ pl : players){
            if(pl.getNickname().equals(nick)){
                pl.setBlock(value);
                return;
            }
        }
    }

    @Override
    public boolean getBlock(String nick) {
        for(Player_ pl : players){
            if(pl.getNickname().equals(nick)){
                return pl.getBlock();
            }
        }
        return false;
    }

    @Override
    public void setPlay(String nick, boolean value) {
        for(Player_ pl : players){
            if(pl.getNickname().equals(nick)){
                pl.setPlay(value);
                return;
            }
        }
    }

    @Override
    public boolean getPlay(String nick) {
        for(Player_ pl : players){
            if(pl.getNickname().equals(nick)){
                return pl.getPlay();
            }
        }
        return false;
    }

    @Override
    public void setReset(String nick, boolean value) {
        for(Player_ pl : players){
            if(pl.getNickname().equals(nick)){
                pl.setReset(value);
                return;
            }
        }
    }

    @Override
    public boolean getReset(String nick) {
        for(Player_ pl : players){
            if(pl.getNickname().equals(nick)){
                return pl.getReset();
            }
        }
        return false;
    }

    @Override
    public boolean getWin(String nick) {
        for(Player_ pl : players){
            if(pl.getNickname().equals(nick)){
                return pl.getWin();
            }
        }
        return false;
    }

    @Override
    public boolean getGameOver(String nick) {
        for(Player_ pl : players){
            if(pl.getNickname().equals(nick)){
                return pl.getGameOver();
            }
        }
        return false;
    }

    @Override
    public int[][] getGrid(String nick) {
        for(Player_ pl : players){
            if(pl.getNickname().equals(nick)){
                return pl.getGrid();
            }
        }
        return new int[0][];
    }

    @Override
    public int[][] getEnemyGrid(String nick) {
        for(Player_ pl : players){
            if(pl.getNickname().equals(nick)){
                return pl.getEnemyGrid();
            }
        }
        return new int[0][];
    }

    @Override
    public void attack(String nick, int x, int y) {
        for(Player_ attacker : players){
            if(attacker.getNickname().equals(nick)){
                for(Player_ pl : players){
                    if(pl.getNickname().equals(attacker.getEnemyNickname())){
                        pl.attack(x,y,attacker);
                    }
                }
            }
        }
    }

    @Override
    public String getEnemyNick(String nick) {
        for(Player_ pl : players){
            if(pl.getNickname().equals(nick)){
                return pl.getEnemyNickname();
            }
        }
        return "";
    }

    @Override
    public void resetMeAndEnemy(String nick) {
        for(Player_ pl : players){
            if(pl.getNickname().equals(nick)){
                for(Player_ pl1 : players){
                    if(pl1.getNickname().equals(pl.getEnemyNickname())){
                        pl1.reset();
                        break;
                    }
                }
                pl.reset();
                return;
            }
        }
    }
}