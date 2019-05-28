class Player_ {
    private String nickname, enemyNickname = "NOTHING";
    private Ship_[] ships;
    private int countShips1 = 0, countShips2 = 0, countShips3 = 0, countShips4 = 0, countDeaded = 0;
    private boolean go = false, play = false, reset = false, gameOver = false, win = false, block = false;
    private String info = "";
    Client_ client;
    private int[][] grid = {{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},};
    private int[][] enemyGrid = {{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},};

    Player_(String nickname, Client_ client){
        this.nickname = nickname;
        ships = new Ship_[10];
        this.client = client;
    }

    int getShipsCount(){
        return countShips1 + countShips2 + countShips3 + countShips4;
    }

    int[][] getGrid(){return grid;}
    int[][] getEnemyGrid() { return enemyGrid;}

    String getInfo() { return info; }

    void setInfo(String info) {
        this.info = info;
    }

    boolean getReset() { return reset; }

    void setReset(boolean reset) {
        this.reset = reset;
    }

    boolean getGo(){
        return go;
    }

    void setGo(boolean go){
        this.go = go;
    }

    boolean getWin(){
        return win;
    }

    boolean getGameOver(){
        return gameOver;
    }

    boolean getBlock(){
        return block;
    }

    void setBlock(boolean block){
        this.block = block;
    }

    boolean getPlay(){
        return play;
    }

    void setPlay(boolean play){
        this.play = play;
    }

    String getNickname(){
        return nickname;
    }

    String getEnemyNickname(){
        return enemyNickname;
    }

    void setEnemyNickname(String enemyNickname){
        this.enemyNickname = enemyNickname;
    }

    boolean addShip(int x, int y, int hor_vert, int length){
        if(!canPut(x, y, hor_vert, length) || (countShips4 == 1 && length == 4) || (countShips3 == 2 && length == 3)
                || (countShips2 == 3 && length == 2) || (countShips1 == 4 && length == 1)) return false;

        for(int i = 0; i < 10; i++)
            if(ships[i] == null) {
                    ships[i] = new Ship_(x, y, hor_vert, length);
                    if(hor_vert == 0) {
                        for (int j = 0; j < length; j++)
                            grid[x + j][y] = 1;
                    }
                    else{
                        for (int j = 0; j < length; j++)
                            grid[x][y + j] = 1;
                    }

                    if(length == 1) countShips1++;
                    else if(length == 2) countShips2++;
                    else if(length == 3) countShips3++;
                    else if(length == 4) countShips4++;

                    return true;
            }
        return false;
    }

    void deleteShip(int x, int y, int hor_vert, int length){
        for (int i = 0; i < 10; i++){
            if(ships[i] == null) continue;
            if(ships[i].getKoords()[0].x == x && ships[i].getKoords()[0].y == y &&
            ships[i].getLength() == length && ships[i].getHor_vert() == hor_vert) {
                if(hor_vert == 0) {
                    for (int j = 0; j < length; j++) {
                        grid[x + j][y] = -1;
                    }
                }
                else {
                    for (int j = 0; j < length; j++) {
                        grid[x][y + j] = -1;
                    }
                }

                ships[i] = null;

                if(length == 1) countShips1--;
                else if(length == 2) countShips2--;
                else if(length == 3) countShips3--;
                else if(length == 4) countShips4--;

                return;
            }
        }
    }

    boolean changeShipPlace(int bX, int bY, int aX, int aY, int hor_vert, int length){
        deleteShip(bX, bY, hor_vert, length);

        if(canPut(aX, aY, hor_vert, length)){
            return addShip(aX, aY, hor_vert, length);
        }

        addShip(bX, bY, hor_vert, length);
        return false;
    }

    private boolean canPut(int x, int y, int hor_vert, int length){
        if(hor_vert == 0) {
            for (int i = 0; i < length; i++) {
                if (x + i > 9 || grid[x + i][y] == 1) return false;
            }
            for (int i = 0; i < length; i++) {
                if (i == 0 && x + i != 0 && grid[x + i - 1][y] == 1) return false;
                if (i == length - 1 && x + i != 9 && grid[x + i + 1][y] == 1) return false;
                if (y != 0 && grid[x + i][y - 1] == 1) return false;
                if (y != 9 && grid[x + i][y + 1] == 1) return false;
            }

            if ((x != 0 && y != 0 && grid[x - 1][y - 1] == 1) ||
                    (x + length != 10 && y != 9 && grid[x + length][y + 1] == 1) ||
                    (x != 0 && y != 9 && grid[x - 1][y + 1] == 1) ||
                    (x + length != 10 && y != 0 && grid[x + length][y - 1] == 1)) return false;
            return true;
        }
        else{
            for (int i = 0; i < length; i++) {
                if (y + i > 9 || grid[x][y + i] == 1) return false;
            }
            for (int i = 0; i < length; i++) {
                if (i == 0 && y + i != 0 && grid[x][y + i - 1] == 1) return false;
                if (i == length - 1 && y + i != 9 && grid[x][y + i + 1] == 1) return false;
                if (x != 0 && grid[x - 1][y + i] == 1) return false;
                if (x != 9 && grid[x + 1][y + i] == 1) return false;
            }

            if ((x != 0 && y != 0 && grid[x - 1][y - 1] == 1) ||
                    (y + length != 10 && x != 9 && grid[x + 1][y + length] == 1) ||
                    (y != 0 && x != 9 && grid[x + 1][y - 1] == 1) ||
                    (y + length != 10 && x != 0 && grid[x - 1][y + length] == 1)) return false;
            return true;
        }
    }

    void attack(int x, int y, Player_ attacker){
        if(grid[x][y] == 2 || grid[x][y] == 3) return;

        if(grid[x][y] == 1) {
            grid[x][y] = 2;

            for(Ship_ sh : ships) {
                for(int i = 0; i < sh.getLength(); i++)
                {
                    if(sh.getKoords()[i].x == x && sh.getKoords()[i].y == y && sh.isAttacked(x,y) && sh.isDead())
                    {
                        countDeaded++;
                        if(sh.getHor_vert() == 0){
                            if(sh.getKoords()[0].x - 1 >= 0) {
                                grid[sh.getKoords()[0].x - 1][sh.getKoords()[0].y] = 3;
                                attacker.enemyGrid[sh.getKoords()[0].x - 1][sh.getKoords()[0].y] = 3;
                            }
                            if(sh.getKoords()[0].x + sh.getLength() <= 9) {
                                grid[sh.getKoords()[0].x + sh.getLength()][sh.getKoords()[0].y] = 3;
                                attacker.enemyGrid[sh.getKoords()[0].x + sh.getLength()][sh.getKoords()[0].y] = 3;
                            }
                            for(int j = sh.getKoords()[0].x - 1; j < sh.getKoords()[sh.getLength() - 1].x + 2; j++){
                                if(y + 1 <= 9 && j >= 0 && j <= 9) {
                                    grid[j][y + 1] = 3;
                                    attacker.enemyGrid[j][y + 1] = 3;
                                }
                                if(y - 1 >= 0 && j >= 0 && j <= 9) {
                                    grid[j][y - 1] = 3;
                                    attacker.enemyGrid[j][y - 1] = 3;
                                }
                            }
                        }
                        else {
                            if(sh.getKoords()[0].y - 1 >= 0) {
                                grid[sh.getKoords()[0].x][sh.getKoords()[0].y - 1] = 3;
                                attacker.enemyGrid[sh.getKoords()[0].x][sh.getKoords()[0].y - 1] = 3;
                            }
                            if(sh.getKoords()[0].y + sh.getLength() <= 9) {
                                grid[sh.getKoords()[0].x][sh.getKoords()[0].y + sh.getLength()] = 3;
                                attacker.enemyGrid[sh.getKoords()[0].x][sh.getKoords()[0].y + sh.getLength()] = 3;
                            }
                            for(int j = sh.getKoords()[0].y - 1; j < sh.getKoords()[sh.getLength() - 1].y + 2; j++){
                                if(x + 1 <= 9 && j >= 0 && j <= 9) {
                                    grid[x + 1][j] = 3;
                                    attacker.enemyGrid[x + 1][j] = 3;
                                }
                                if(x - 1 >= 0 && j >= 0 && j <= 9) {
                                    grid[x - 1][j] = 3;
                                    attacker.enemyGrid[x - 1][j] = 3;
                                }
                            }
                        }
                    }
                }
            }

            attacker.enemyGrid[x][y] = 2;
        }
        else{
            grid[x][y] = 3;
            attacker.enemyGrid[x][y] = 3;
            go = true;
            info = "Ваш ход...";
            attacker.go = false;
            attacker.info = "Ход противника...";
        }

        if(countDeaded == 10){
            gameOver = true;
            attacker.win = true;
        }
    }

    void reset(){
        enemyNickname = "NOTHING";
        countShips1 = 0;
        countShips2 = 0;
        countShips3 = 0;
        countShips4 = 0;
        countDeaded = 0;
        go = false;
        play = false; block = false;
        reset = true;
        gameOver = false;
        win = false;

        for(int i = 0; i < 10; i++) {
            ships[i] = null;
            for (int j = 0; j < 10; j++) {
                grid[i][j] = -1;
                enemyGrid[i][j] = -1;
            }
        }
    }
}
