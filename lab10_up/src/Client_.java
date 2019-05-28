import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;

public class Client_ extends JFrame {
    private static SeaFight_ service;
    private static String nick = "";

    public static void main(String[] args) {
        if(args.length != 1) {
            JOptionPane.showMessageDialog(null,"Недопустимое число аргументов!");
            System.exit(0);
        }

        try {
            Registry registry = LocateRegistry.getRegistry(Integer.parseInt(args[0]));
            service = (SeaFight_) registry.lookup("ClientRegister");
        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(null, "Сервер не запущен либо возникла " +
                    "неизвестная ошибка.");
            System.exit(0);
        }
        catch (RemoteException | NotBoundException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);
        }

        new Client_();
    }

    private Client_() {
        super("Морской бой");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        JTextField enemyNickField = new JTextField();
        JButton closeButton = new JButton("Выйти");
        JButton playButton = new JButton("В бой!");

        closeButton.addActionListener(e -> {
            try {
                service.playerUnregister(nick);
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                this.dispose();
                System.exit(0);
            }
            catch (RemoteException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage());
            }
            this.dispose();
            System.exit(0);
        });

        Canvas canvas = new Canvas();

        playButton.addActionListener(e -> {
            int shipsCount = 0;
            try {
                shipsCount = service.getShipsCount(nick);
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                this.dispose();
                System.exit(0);
            }catch (RemoteException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage());
            }

            if(shipsCount != 10)
                JOptionPane.showMessageDialog(this, "Недостаточно кораблей!");
            else {
                String text = enemyNickField.getText();
                if(text.equals("NOTHING")){
                    JOptionPane.showMessageDialog(this, "Вы не можете иметь такое имя.");
                    return;
                }

                int res = 0;
                try {
                    res = service.setEnemyNickname(nick, text);
                } catch (ConnectException e1) {
                    JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                    this.dispose();
                    System.exit(0);
                }catch (RemoteException e1) {
                    JOptionPane.showMessageDialog(this, e1.getMessage());
                }

                if (res == 0) JOptionPane.showMessageDialog(this, "Неизвестная ошибка.");
                else if (res == -1) JOptionPane.showMessageDialog(this, "Пустая строка!");
                else if (res == -2) JOptionPane.showMessageDialog(this,
                        "Пользователь " + text + " не в сети.");
                else if (res == -3) JOptionPane.showMessageDialog(this,
                        "Вы не можете играть с собой!");
                else {
                    int enemyShipsCount = 0;
                    try {
                        enemyShipsCount = service.getShipsCount(text);
                    } catch (ConnectException e1) {
                        JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                        this.dispose();
                        System.exit(0);
                    }catch (RemoteException e1) {
                        JOptionPane.showMessageDialog(this, e1.getMessage());
                    }
                    if(enemyShipsCount != 10){
                        try {
                            service.setInfo(nick, text + " получил сообщение, но у " +
                                    "него недостаточно кораблей на поле. Вы можете попытаться вызвать его позже либо " +
                                    "дождаться вызова с его стороны.");
                        } catch (ConnectException e1) {
                            JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                            this.dispose();
                            System.exit(0);
                        }catch (RemoteException e1) {
                            JOptionPane.showMessageDialog(this, e1.getMessage());
                        }

                        try {
                            service.setInfo(text, nick + " пригласил вас в игру. Чтобы сыграть, " +
                                    "расставьте корабли на поле и вызовите его либо дождитесь повторного вызова.");
                        } catch (ConnectException e1) {
                            JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                            this.dispose();
                            System.exit(0);
                        }catch (RemoteException e1) {
                            JOptionPane.showMessageDialog(this, e1.getMessage());
                        }
                    }
                    else {
                        try {
                            service.setInfo(nick, "Ожидание ответа от " + text + "...");
                        } catch (ConnectException e1) {
                            JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                            this.dispose();
                            System.exit(0);
                        }catch (RemoteException e1) {
                            JOptionPane.showMessageDialog(this, e1.getMessage());
                        }

                        try {
                            service.setBlock(nick, true);
                        } catch (ConnectException e1) {
                            JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                            this.dispose();
                            System.exit(0);
                        }catch (RemoteException e1) {
                            JOptionPane.showMessageDialog(this, e1.getMessage());
                        }

                        try {
                            service.call(nick, text, service);
                        } catch (ConnectException e1) {
                            JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                            this.dispose();
                            System.exit(0);
                        }catch (RemoteException e1) {
                            JOptionPane.showMessageDialog(this, e1.getMessage());
                        }
                    }
                }
            }
        });

        JLabel label = new JLabel("Никнейм соперника:");
        label.setSize(120, 25);
        enemyNickField.setSize(100, 25);
        closeButton.setSize(100, 25);
        playButton.setSize(100, 25);
        label.setLocation(220, 400);
        enemyNickField.setLocation(350, 400);
        closeButton.setLocation(460, 400);
        playButton.setLocation(570, 400);

        JButton endGame = new JButton("Закончить игру");
        endGame.setSize(200, 25);
        endGame.setLocation(570, 400);
        endGame.addActionListener(e -> {
            try {
                service.setReset(nick, true);
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                this.dispose();
                System.exit(0);
            }catch (RemoteException e1) {
                JOptionPane.showMessageDialog(getParent(), e1.getMessage());
            }
            try {
                service.setReset(service.getEnemyNick(nick), true);
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                this.dispose();
                System.exit(0);
            }catch (RemoteException e1) {
                JOptionPane.showMessageDialog(getParent(), e1.getMessage());
            }

            try {
                service.setInfo(service.getEnemyNick(nick), nick + " сдался.");
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                this.dispose();
                System.exit(0);
            }catch (RemoteException e1) {
                JOptionPane.showMessageDialog(getParent(), e1.getMessage());
            }
            try {
                service.setInfo(nick, "");
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                this.dispose();
                System.exit(0);
            }catch (RemoteException e1) {
                JOptionPane.showMessageDialog(getParent(), e1.getMessage());
            }

            try {
                service.resetMeAndEnemy(nick);
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                this.dispose();
                System.exit(0);
            }catch (RemoteException e1) {
                JOptionPane.showMessageDialog(getParent(), e1.getMessage());
            }

            label.setVisible(true);
            enemyNickField.setVisible(true);
            closeButton.setVisible(true);
            playButton.setVisible(true);
            endGame.setVisible(false);
        });

        canvas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                canvas.clicked(e.getX(), e.getY(), service);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        canvas.add(label);
        canvas.add(enemyNickField);
        canvas.add(closeButton);
        canvas.add(playButton);
        endGame.setVisible(false);
        canvas.add(endGame);

        class Task extends TimerTask {

            @Override
            public void run() {
                canvas.clicked(0,0,service);

                try {
                    if(service.getBlock(nick)){
                        closeButton.setEnabled(false);
                        playButton.setEnabled(false);
                    }
                    else {
                        closeButton.setEnabled(true);
                        playButton.setEnabled(true);
                    }
                } catch (ConnectException e1) {
                    JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                    System.exit(0);
                }catch (RemoteException e) {
                    JOptionPane.showMessageDialog(getParent(), e.getMessage());
                }

                try {
                    if(service.getWin(nick)){
                        label.setVisible(true);
                        enemyNickField.setVisible(true);
                        closeButton.setVisible(true);
                        playButton.setVisible(true);
                        endGame.setVisible(false);
                        service.setInfo(nick,"ВЫ ПОБЕДИЛИ ИГРОКА " + service.getEnemyNick(nick) + "!");
                        service.setInfo(service.getEnemyNick(nick), "ВЫ ПРОИГРАЛИ ИГРОКУ " + nick + "!");
                        service.resetMeAndEnemy(nick);
                    }
                } catch (ConnectException e1) {
                    JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                    System.exit(0);
                }catch (RemoteException e) {
                    JOptionPane.showMessageDialog(getParent(), e.getMessage());
                }
                try {
                    if(service.getGameOver(nick)){
                        label.setVisible(true);
                        enemyNickField.setVisible(true);
                        closeButton.setVisible(true);
                        playButton.setVisible(true);
                        endGame.setVisible(false);
                    }
                } catch (ConnectException e1) {
                    JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                    System.exit(0);
                }catch (RemoteException e) {
                    JOptionPane.showMessageDialog(getParent(), e.getMessage());
                }

                try {
                    if(service.getReset(nick)){
                        canvas.reset();
                        service.setReset(nick,false);
                    }
                } catch (ConnectException e1) {
                    JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                    System.exit(0);
                }catch (RemoteException e) {
                    JOptionPane.showMessageDialog(getParent(), e.getMessage());
                }

                try {
                    if(service.getPlay(nick)){
                        label.setVisible(false);
                        enemyNickField.setVisible(false);
                        closeButton.setVisible(false);
                        playButton.setVisible(false);
                        endGame.setVisible(true);
                    }
                    else {
                        label.setVisible(true);
                        enemyNickField.setVisible(true);
                        closeButton.setVisible(true);
                        playButton.setVisible(true);
                        endGame.setVisible(false);
                    }
                } catch (ConnectException e1) {
                    JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                    System.exit(0);
                }catch (RemoteException e) {
                    JOptionPane.showMessageDialog(getParent(), e.getMessage());
                }
            }
        }

        Timer timer = new Timer();
        timer.schedule( new Task(),0,300 );

        this.add(canvas);
        setSize(900, 500);
        setVisible(true);

        JDialog registry = createRegistryDialog();
        registry.setVisible(true);
    }

    private JDialog createRegistryDialog() {
        JDialog dialog = new JDialog(this, "Регистрация", true);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        panel.add(new Label("Ваш никнейм:"));
        JTextField nickField = new JTextField();
        panel.add(nickField);
        JButton close = new JButton("Отмена");
        close.addActionListener(e -> {
            dialog.dispose();
            this.dispose();
            System.exit(0);
        });
        panel.add(close);
        JButton reg = new JButton("Регистрация");
        reg.addActionListener(e -> {
            nick = nickField.getText();

            int res = 1;
            try {
                res = service.playerRegister(nick, this);
            }catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                dialog.dispose();
                this.dispose();
                System.exit(0);
            } catch (RemoteException e1) {
                JOptionPane.showMessageDialog(dialog, e1.getMessage());
            }
            if (res == -1) JOptionPane.showMessageDialog(dialog, "Пустая строка!");
            else if (res == -2) JOptionPane.showMessageDialog(dialog, "Никнейм занят!");
            else {
                dialog.dispose();
            }

        });
        panel.add(reg);

        dialog.setContentPane(panel);
        dialog.setSize(300, 100);
        return dialog;
    }

    JFrame createAskFrame(String whoWantPlay, String whoAnswer,  SeaFight_ serv){
        JFrame fr = new JFrame("Вызов");
        fr.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        boolean[] timeOut = {true};
        class Task extends TimerTask {

            @Override
            public void run() {
                if(timeOut[0]) {
                    try {
                        serv.setBlock(whoWantPlay, false);
                    } catch (RemoteException e1) {
                        JOptionPane.showMessageDialog(fr, e1.getMessage());
                    }
                    try {
                        serv.setInfo(whoWantPlay, "Время ожидания ответа от " + whoAnswer + " вышло.");
                    } catch (ConnectException e1) {
                        JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                        System.exit(0);
                    }catch (RemoteException e1) {
                        JOptionPane.showMessageDialog(fr, e1.getMessage());
                    }
                    try {
                        serv.setInfo(whoAnswer, whoWantPlay + " приглашал вас в игру, но вы не дали ответа.");
                    } catch (ConnectException e1) {
                        JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                        System.exit(0);
                    }catch (RemoteException e1) {
                        JOptionPane.showMessageDialog(fr, e1.getMessage());
                    }

                    fr.dispose();
                }
            }
        }
        Timer timer = new Timer();
        timer.schedule(new Task(), 60000);

        JPanel panel = new JPanel();
        JLabel label = new JLabel(whoWantPlay + " хочет сыграть с вами. Согласны?");
        JButton yes = new JButton("Да");
        yes.addActionListener(e -> {
            try {
                serv.setBlock(whoWantPlay, false);
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                fr.dispose();
                this.dispose();
                System.exit(0);
            }catch (RemoteException e1) {
                JOptionPane.showMessageDialog(fr, e1.getMessage());
            }
            timeOut[0] = false;
            fr.dispose();
            try {
                serv.setInfo(whoWantPlay, "Ход противника...");
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                fr.dispose();
                this.dispose();
                System.exit(0);
            }catch (RemoteException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage());
            }
            try {
                serv.setInfo(whoAnswer, "Ваш ход...");
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                fr.dispose();
                this.dispose();
                System.exit(0);
            }catch (RemoteException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage());
            }
            try {
                serv.setGo(whoAnswer, true);
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                fr.dispose();
                this.dispose();
                System.exit(0);
            }catch (RemoteException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage());
            }
            try {
                serv.setPlay(whoAnswer, true);
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                fr.dispose();
                this.dispose();
                System.exit(0);
            }catch (RemoteException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage());
            }
            try {
                serv.setPlay(whoWantPlay, true);
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                fr.dispose();
                this.dispose();
                System.exit(0);
            }catch (RemoteException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage());
            }
            try {
                serv.setEnemyNickname(whoWantPlay, whoAnswer);
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                fr.dispose();
                this.dispose();
                System.exit(0);
            }catch (RemoteException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage());
            }
            try {
                serv.setEnemyNickname(whoAnswer, whoWantPlay);
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                fr.dispose();
                this.dispose();
                System.exit(0);
            }catch (RemoteException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage());
            }
        });
        JButton no = new JButton("Нет");
        no.addActionListener(e -> {
            try {
                serv.setBlock(whoWantPlay, false);
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                fr.dispose();
                this.dispose();
                System.exit(0);
            }catch (RemoteException e1) {
                JOptionPane.showMessageDialog(fr, e1.getMessage());
            }
            timeOut[0] = false;
            fr.dispose();
            try {
                serv.setInfo(whoWantPlay, whoAnswer + " отказался играть.");
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                fr.dispose();
                this.dispose();
                System.exit(0);
            }catch (RemoteException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage());
            }
        });
        label.setLocation(0,0);
        yes.setLocation(0, 50);
        no.setLocation(100, 50);
        panel.add(label);
        panel.add(yes);
        panel.add(no);
        fr.setContentPane(panel);

        fr.setSize(350, 100);
        return fr;
    }

    class Canvas extends JComponent {

        private int h = 20, selectedShipNumber = -1;
        private boolean selectedShipIsOnGrid = false;
        private String text = "";
        private boolean play = false;
        private int[][] enemyGrid, grid;

        private Ship_[] buildShips = {new Ship_(12, 1, 0, 1),
                new Ship_(14, 1, 0, 1),
                new Ship_(16, 1, 0, 1),
                new Ship_(18, 1, 0, 1),

                new Ship_(20, 1, 0, 2),
                new Ship_(23, 1, 0, 2),
                new Ship_(26, 1, 0, 2),
                new Ship_(12, 3, 0, 3),
                new Ship_(16, 3, 0, 3),
                new Ship_(20, 3, 0, 4),

                new Ship_(12, 5, 1, 4),
                new Ship_(14, 5, 1, 3),
                new Ship_(16, 5, 1, 3),
                new Ship_(18, 5, 1, 2),
                new Ship_(20, 5, 1, 2),
                new Ship_(22, 5, 1, 2)};

        final private Ship_[] borders = {new Ship_(12, 1, 0, 1),
                new Ship_(14, 1, 0, 1),
                new Ship_(16, 1, 0, 1),
                new Ship_(18, 1, 0, 1),

                new Ship_(20, 1, 0, 2),
                new Ship_(23, 1, 0, 2),
                new Ship_(26, 1, 0, 2),
                new Ship_(12, 3, 0, 3),
                new Ship_(16, 3, 0, 3),
                new Ship_(20, 3, 0, 4),

                new Ship_(12, 5, 1, 4),
                new Ship_(14, 5, 1, 3),
                new Ship_(16, 5, 1, 3),
                new Ship_(18, 5, 1, 2),
                new Ship_(20, 5, 1, 2),
                new Ship_(22, 5, 1, 2)};

        public void paintComponent(Graphics g) {
            super.paintComponents(g);
            Graphics2D g2d = (Graphics2D) g;

            char c = 'а';
            for (int i = 1; i < 12; i++) {
                if(i != 11) g2d.drawString(i + "", i * h + 7, h - 2);
                if(i != 11) g2d.drawString(c++ + "", 8, i * h + h / 2 + 5);
                g2d.drawLine(i * h, h, i * h, h * 11);
                g2d.drawLine(h, i * h, h * 11, i * h);
            }

            if(play){
                c = 'а';
                for(int i = 13; i < 24; i++) {
                    if(i != 23) g2d.drawString((i - 12) + "", i * h + 7, h - 2);
                    if(i != 23) g2d.drawString(c++ + "", 13 * h - 12, (i - 12) * h + h / 2 + 5);
                    g2d.drawLine(i * h, h, i * h, h * 11);
                    g2d.drawLine(13 * h, (i-12) * h, h * 23, (i-12) * h);
                }

                for(int i = 0; i < 10; i++)
                    for(int j = 0; j < 10; j++)
                    {
                        int m = grid[i][j];
                        if(m == 1) g2d.fillRect((i+1)*h, (j+1)*h, h, h);
                        else if(m == 2){
                            g2d.drawLine((i+1)*h, (j+1)*h, (i+2)*h, (j+2)*h);
                            g2d.drawLine((i+1)*h, (j+2)*h, (i+2)*h, (j+1)*h);
                        }
                        else if(m == 3)g2d.fillOval((i+1)*h+h/4, (j+1)*h+h/4, h/2, h/2);
                    }

                for(int i = 12; i < 22; i++)
                    for(int j = 0; j < 10; j++)
                    {
                        int m = enemyGrid[i-12][j];
                        if(m == 1) g2d.fillRect((i+1)*h, (j+1)*h, h, h);
                        else if(m == 2){
                            g2d.drawLine((i+1)*h, (j+1)*h, (i+2)*h, (j+2)*h);
                            g2d.drawLine((i+1)*h, (j+2)*h, (i+2)*h, (j+1)*h);
                        }
                        else if(m == 3)g2d.fillOval((i+1)*h+h/4, (j+1)*h+h/4, h/2, h/2);
                    }
            }
            else {
                for (Ship_ sh : borders) {
                    if (sh.getHor_vert() == 0)
                        g2d.drawRect(h * sh.getKoords()[0].x, h * sh.getKoords()[0].y, h * sh.getLength(), h);
                    else
                        g2d.drawRect(h * sh.getKoords()[0].x, h * sh.getKoords()[0].y, h, h * sh.getLength());
                }

                for (Ship_ sh : buildShips) {
                    if (sh.getHor_vert() == 0)
                        g2d.fillRect(h * sh.getKoords()[0].x, h * sh.getKoords()[0].y, h * sh.getLength(), h);
                    else
                        g2d.fillRect(h * sh.getKoords()[0].x, h * sh.getKoords()[0].y, h, h * sh.getLength());
                }

                if (selectedShipNumber != -1) {
                    if (buildShips[selectedShipNumber].getHor_vert() == 0)
                        g2d.drawRect(buildShips[selectedShipNumber].getKoords()[0].x * h - 3,
                                buildShips[selectedShipNumber].getKoords()[0].y * h - 3,
                                h * buildShips[selectedShipNumber].getLength() + 6, h + 6);
                    else g2d.drawRect(buildShips[selectedShipNumber].getKoords()[0].x * h - 3,
                            buildShips[selectedShipNumber].getKoords()[0].y * h - 3,
                            h + 6, h * buildShips[selectedShipNumber].getLength() + 6);
                }
            }
            g2d.drawString(text, 20,300);
        }

        void clicked(int x, int y, SeaFight_ serv){
            try {
                play = serv.getPlay(nick);
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                System.exit(0);
            }catch (RemoteException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
            try {
                enemyGrid = serv.getEnemyGrid(nick);
            }catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                System.exit(0);
            } catch (RemoteException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
            try {
                grid = serv.getGrid(nick);
            } catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                System.exit(0);
            }catch (RemoteException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
            try {
                text = serv.getInfo(nick);
            }catch (ConnectException e1) {
                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                System.exit(0);
            } catch (RemoteException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
            boolean selectedNow = false;
            for(int i = 0; i < buildShips.length; i++){
                if(buildShips[i].getHor_vert() == 0 && x >= h * buildShips[i].getKoords()[0].x &&
                        x <= h * buildShips[i].getKoords()[0].x + h * buildShips[i].getLength() &&
                        y >= h * buildShips[i].getKoords()[0].y && y <= h * buildShips[i].getKoords()[0].y + h){
                    selectedShipNumber = i;
                    selectedNow = true;
                    break;
                }
                else if(buildShips[i].getHor_vert() == 1 && x >= h * buildShips[i].getKoords()[0].x &&
                        x <= h * buildShips[i].getKoords()[0].x + h && y >= h * buildShips[i].getKoords()[0].y
                        && y <= h * buildShips[i].getKoords()[0].y + h * buildShips[i].getLength()){
                    selectedShipNumber = i;
                    selectedNow = true;
                    break;
                }
            }

            if(selectedNow && x >= h && x <= h * 11 && y >= h && y <= h * 11){
                selectedShipIsOnGrid = true;
            }

            if(!selectedNow && selectedShipNumber != -1 && x >= h && x <= h * 11 && y >= h && y <= h * 11){
                for(int n = h; n <= 10 * h; n += h)
                    for(int m = h; m <= 10 * h; m += h)
                        if(selectedShipNumber != -1 && x >= n && x <= n + h && y >= m && y <= m + h){
                            Ship_ sh;
                            Point[] newKoords = new Point[buildShips[selectedShipNumber].getLength()];
                            if(buildShips[selectedShipNumber].getHor_vert() == 0){
                                for(int j = 0; j < buildShips[selectedShipNumber].getLength(); j++)
                                    newKoords[j] = new Point(n / h + j - 1, m / h - 1);
                            }
                            else{
                                for(int j = 0; j < buildShips[selectedShipNumber].getLength(); j++)
                                    newKoords[j] = new Point(n / h - 1, m / h + j - 1);
                            }
                            sh = new Ship_(newKoords[0].x, newKoords[0].y,
                                    buildShips[selectedShipNumber].getHor_vert(),
                                    buildShips[selectedShipNumber].getLength());

                            boolean canPut = false;
                            if(selectedShipIsOnGrid){
                                try {
                                    canPut = serv.changeShipPlace(nick, buildShips[selectedShipNumber],
                                            sh.getKoords()[0].x, sh.getKoords()[0].y);
                                } catch (ConnectException e1) {
                                    JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                                    System.exit(0);
                                }catch (RemoteException e) {
                                    JOptionPane.showMessageDialog(this, e.getMessage());
                                }
                            }
                            else {
                                try {
                                    canPut = serv.addShipToPlayer(nick, sh);
                                } catch (ConnectException e1) {
                                    JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                                    System.exit(0);
                                }catch (RemoteException e) {
                                    JOptionPane.showMessageDialog(this, e.getMessage());
                                }
                            }

                            if(canPut) {
                                for(Point p : newKoords){
                                    p.x += 1;
                                    p.y += 1;
                                }
                                buildShips[selectedShipNumber].setKoords(newKoords);
                            }
                            selectedShipNumber = -1;
                            break;
                        }
            }
            else if(!selectedNow && selectedShipNumber != -1){
                for (Ship_ border : borders) {
                    if ((border.getHor_vert() == 0 && x >= h * border.getKoords()[0].x &&
                            x <= h * border.getKoords()[0].x + h * border.getLength() &&
                            y >= h * border.getKoords()[0].y && y <= h * border.getKoords()[0].y + h) ||
                            (border.getHor_vert() == 1 && x >= h * border.getKoords()[0].x &&
                            x <= h * border.getKoords()[0].x + h && y >= h * border.getKoords()[0].y
                            && y <= h * border.getKoords()[0].y + h * border.getLength())) {

                        if (buildShips[selectedShipNumber].getLength() == border.getLength() &&
                                buildShips[selectedShipNumber].getHor_vert() == border.getHor_vert()) {
                            try {
                                serv.deletePlayersShip(nick, buildShips[selectedShipNumber]);
                            } catch (ConnectException e1) {
                                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                                System.exit(0);
                            }catch (RemoteException e) {
                                JOptionPane.showMessageDialog(this, e.getMessage());
                            }
                            buildShips[selectedShipNumber].setKoords(border.getKoords());
                            break;
                        }

                        selectedShipNumber = -1;
                        break;
                    }
                }
            }
            if(play && x >= 13 * h && x <= 23 * h && y >= h && y <= 11 * h) {
                for(int n = 13 * h; n <= 22 * h; n += h)
                    for(int m = h; m <= 10 * h; m += h)
                        if(x >= n && x <= n + h && y >= m && y <= m + h){
                            try {
                                if (serv.getGo(nick)) {
                                    serv.attack(nick, n / h - 13, m / h - 1);
                                }
                            } catch (ConnectException e1) {
                                JOptionPane.showMessageDialog(null, "Проблемы с сервером.");
                                System.exit(0);
                            }catch (RemoteException e) {
                                JOptionPane.showMessageDialog(this, e.getMessage());
                            }
                            break;
                        }
            }
            repaint();
        }

        void reset() {
            h = 20;
            selectedShipNumber = -1;
            selectedShipIsOnGrid = false;
            text = "";
            play = false;

            buildShips[0] = new Ship_(12, 1, 0, 1);
            buildShips[1] = new Ship_(14, 1, 0, 1);
            buildShips[2] = new Ship_(16, 1, 0, 1);
            buildShips[3] = new Ship_(18, 1, 0, 1);

            buildShips[4] = new Ship_(20, 1, 0, 2);
            buildShips[5] = new Ship_(23, 1, 0, 2);
            buildShips[6] = new Ship_(26, 1, 0, 2);
            buildShips[7] = new Ship_(12, 3, 0, 3);
            buildShips[8] = new Ship_(16, 3, 0, 3);
            buildShips[9] = new Ship_(20, 3, 0, 4);

            buildShips[10] = new Ship_(12, 5, 1, 4);
            buildShips[11] = new Ship_(14, 5, 1, 3);
            buildShips[12] = new Ship_(16, 5, 1, 3);
            buildShips[13] = new Ship_(18, 5, 1, 2);
            buildShips[14] = new Ship_(20, 5, 1, 2);
            buildShips[15] = new Ship_(22, 5, 1, 2);

            borders[0] = new Ship_(12, 1, 0, 1);
            borders[1] = new Ship_(14, 1, 0, 1);
            borders[2] = new Ship_(16, 1, 0, 1);
            borders[3] = new Ship_(18, 1, 0, 1);

            borders[4] = new Ship_(20, 1, 0, 2);
            borders[5] = new Ship_(23, 1, 0, 2);
            borders[6] = new Ship_(26, 1, 0, 2);
            borders[7] = new Ship_(12, 3, 0, 3);
            borders[8] = new Ship_(16, 3, 0, 3);
            borders[9] = new Ship_(20, 3, 0, 4);

            borders[10] = new Ship_(12, 5, 1, 4);
            borders[11] = new Ship_(14, 5, 1, 3);
            borders[12] = new Ship_(16, 5, 1, 3);
            borders[13] = new Ship_(18, 5, 1, 2);
            borders[14] = new Ship_(20, 5, 1, 2);
            borders[15] = new Ship_(22, 5, 1, 2);

            repaint();
        }
    }
}