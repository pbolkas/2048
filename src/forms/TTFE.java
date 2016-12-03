package forms;

import java.awt.Color;
import static java.awt.Color.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JLabel;

public class TTFE extends javax.swing.JFrame {

    /**
     * Creates new form TTFE
     */
    private int table[][] = new int[4][4];

    Random r = new Random();

    public void initTable() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                table[i][j] = 0;//init all cells at 0
            }
        }
        int x = r.nextInt(4 - 0);//generate random number between 0-3
        int y = r.nextInt(4 - 0);
        System.out.println("first label that will be filled is " + x + "," + y);
        table[x][y] = 2;

        int x2 = r.nextInt(4 - 0);
        int y2 = r.nextInt(4 - 0);

        //se periptwsi pou vgoun idia ta kelia
        while (x2 == x && y2 == y) {
            x2 = r.nextInt(4 - 0);
            y2 = r.nextInt(4 - 0);
        }
        System.out.println("second label that will be filled is " + x2 + "," + y2);
        table[x2][y2] = 2;
    }

    public Thread updateLabel = new Thread() {
        @Override

        public synchronized void run() {
            updateGame();
            setColors();
        }
    };
    
    //checks is there is 2048 succeded and show a messagebox, if not lets user to play
    public boolean checkIfWon(){
        return true;
    }
    
    //checks is table is full and there are not any other moves and shows message box, if not lets user play
    public boolean checkifFull(){
        return true;
    }

    public synchronized void updateGame() {
        //first line
        zerozero.setText((table[0][0] == 0) ? "" : table[0][0] + "");
        zeroone.setText((table[0][1] == 0) ? "" : table[0][1] + "");
        zerotwo.setText((table[0][2] == 0) ? "" : table[0][2] + "");
        zerothree.setText((table[0][3] == 0) ? "" : table[0][3] + "");

        //second line
        onezero.setText((table[1][0] == 0) ? "" : table[1][0] + "");
        oneone.setText((table[1][1] == 0) ? "" : table[1][1] + "");
        onetwo.setText((table[1][2] == 0) ? "" : table[1][2] + "");
        onethree.setText((table[1][3] == 0) ? "" : table[1][3] + "");

        //third line
        twozero.setText((table[2][0] == 0) ? "" : table[2][0] + "");
        twoone.setText((table[2][1] == 0) ? "" : table[2][1] + "");
        twotwo.setText((table[2][2] == 0) ? "" : table[2][2] + "");
        twothree.setText((table[2][3] == 0) ? "" : table[2][3] + "");

        //fourth line
        threezero.setText((table[3][0] == 0) ? "" : table[3][0] + "");
        threeone.setText((table[3][1] == 0) ? "" : table[3][1] + "");
        threetwo.setText((table[3][2] == 0) ? "" : table[3][2] + "");
        threethree.setText((table[3][3] == 0) ? "" : table[3][3] + "");

        //update score
        int sum = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                sum += table[i][j];
            }
        }
        this.theScore.setText(sum + "");
    }

    public void printTable() {//prints table in console
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(table[i][j]);
            }
            System.out.println("");
        }
    }

    public int[][] rotate90() { //rotates clockwise the table to make the changes (merges, moves between cells)
        int[][] newTable = new int[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                newTable[i][j] = table[4 - j - 1][i];
            }
        }
        return newTable;
    }

    private boolean isMovePossible() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < (4 - 1); y++) {
                int yy = y + 1;
                if (table[x][y] == table[x][yy]) {
                    return true;
                }
            }
        }

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < (4 - 1); x++) {
                int xx = x + 1;
                if (table[x][y] == table[xx][y]) {
                    return true;
                }
            }
        }

        return false;
    }

    public void merge() {

        for (int i = 0; i < table.length; i++) {
            int temp[] = new int[table[i].length];
            for (int j = 0; j < table[i].length; j++) {
                temp[j] = table[j][i];
            }
            temp = this.reArrange(temp);

            for (int j = 0; j < table[i].length; j++) {
                table[j][i] = temp[j];
            }

        }
    }

    public void insertRandomly() {
        int x = r.nextInt(4 - 0);
        int y = r.nextInt(4 - 0);

        while (table[x][y] != 0) {
            x = r.nextInt(4 - 0);
            y = r.nextInt(4 - 0);
        }

        table[x][y] = getNextNumberAdded();
    }

    private int getNextNumberAdded() {
        //h pithnotita na prostethei to 4 einai 10%
        //enw h pithanotita na prostethei to 2 einai 90%
        int p = r.nextInt(101 - 1);
        if (p > 90) {
            return 4;
        } else {
            return 2;
        }
    }

    //pushes all 0 cells to the end of the array
    private int[] reArrange(int arr[]) {
        int newArr[] = new int[arr.length];

        int count = 0;  // Count of non-zero elements

        // Traverse the array. If element encountered is
        // non-zero, then replace the element at index 'count'
        // with this element
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                arr[count++] = arr[i]; // here count is
            }                                       // incremented
        }
        // Now all non-zero elements have been shifted to
        // front and 'count' is set as index of first 0.
        // Make all elements 0 from count to end.
        while (count < arr.length) {
            arr[count++] = 0;
        }
        //System.out.println(Arrays.toString(arr));
        return arr;

    }

    //adds two cells if they have the same value
    public void makeCalculations() {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length - 1; j++) {
                if (table[j][i] == table[j + 1][i]) {
                    table[j][i] *= 2;
                    table[j + 1][i] = 0;
                }
            }
        }
    }

    //while pressed up arrow or click on up button
    public void pressedUp() {
        if (this.isMovePossible()) {
            System.out.println("pressed up");
            this.merge();
            this.insertRandomly();
            this.merge();
            this.makeCalculations();
            this.updateLabel.run();
        }

    }

    //called when pressed down arrow or click down button
    public void pressedDown() {
        if (this.isMovePossible()) {
            System.out.println("pressed down");
            this.table = this.rotate90();
            this.table = this.rotate90();

            this.merge();
            this.insertRandomly();
            this.merge();
            this.makeCalculations();

            this.table = this.rotate90();
            this.table = this.rotate90();

            this.updateLabel.run();
        }
    }

    //called when pressed left arrow or click left button
    public void pressedRight() {
        if (this.isMovePossible()) {
            System.out.println("pressed left");
            this.table = this.rotate90();
            this.table = this.rotate90();
            this.table = this.rotate90();

            this.merge();
            this.insertRandomly();
            this.merge();
            this.makeCalculations();

            this.table = this.rotate90();

            this.updateLabel.run();
        }

    }

    //called when pressed right arrow or click right butto
    public void pressedLeft() {
        if (this.isMovePossible()) {
            System.out.println("pressed right");
            this.table = this.rotate90();

            this.merge();
            this.insertRandomly();
            this.merge();
            this.makeCalculations();

            this.table = this.rotate90();
            this.table = this.rotate90();
            this.table = this.rotate90();

            this.updateLabel.run();
        }
    }

    public TTFE() {
        initComponents();
        initTable();
        updateLabel.start();
        this.setFocusable(true);
        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    pressedUp();
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    pressedDown();
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    pressedLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    pressedRight();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

        });
    }

    //methodos gia xrwmatismo twn arithmwn se ola ta kelia analoga me tin timi 
    public void setColors() {
        JLabel[] labels = {zerozero, zeroone, zerotwo, zerothree, onezero, oneone, onetwo, onethree,
            twozero, twoone, twotwo, twothree, threezero, threeone, threetwo, threethree};
        int num = 2;

        Color[] colors = new Color[11];

        //Initialize the values of the array
        colors[0] = Color.red;
        colors[1] = Color.orange;
        colors[2] = Color.yellow;
        colors[3] = Color.green;
        colors[4] = Color.pink;
        colors[5] = Color.blue;
        colors[6] = Color.MAGENTA;
        colors[7] = Color.CYAN;
        colors[8] = Color.decode("0xFF0096");
        colors[9] = Color.decode("0xFF8979");
        colors[10] = Color.decode("0x343434");
        
        for (int i = 0; i < labels.length; i++) {
            for (num = 2; num < 2049; num *= 2) {
                if (labels[i].getText().equals(num + "")) {
                    labels[i].setForeground(colors[(TTFE.log2(num)) - 1]);
                }
            }
        }
    }

    private static final int log2(float f) {
        return (int) Math.floor(Math.log(f) / Math.log(2.0));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        parent = new javax.swing.JPanel();
        gamePanel = new javax.swing.JPanel();
        zerozero = new javax.swing.JLabel();
        zeroone = new javax.swing.JLabel();
        zerothree = new javax.swing.JLabel();
        onezero = new javax.swing.JLabel();
        oneone = new javax.swing.JLabel();
        onetwo = new javax.swing.JLabel();
        twothree = new javax.swing.JLabel();
        zerotwo = new javax.swing.JLabel();
        twozero = new javax.swing.JLabel();
        twoone = new javax.swing.JLabel();
        twotwo = new javax.swing.JLabel();
        onethree = new javax.swing.JLabel();
        threezero = new javax.swing.JLabel();
        threeone = new javax.swing.JLabel();
        threetwo = new javax.swing.JLabel();
        threethree = new javax.swing.JLabel();
        newGame = new javax.swing.JButton();
        currentScoreLabel = new javax.swing.JLabel();
        theScore = new javax.swing.JLabel();
        controls = new javax.swing.JPanel();
        up = new javax.swing.JButton();
        down = new javax.swing.JButton();
        left = new javax.swing.JButton();
        right = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(500, 500));
        setSize(new java.awt.Dimension(500, 500));

        parent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                parentKeyPressed(evt);
            }
        });

        gamePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gamePanel.setPreferredSize(new java.awt.Dimension(300, 300));
        gamePanel.setLayout(new java.awt.GridBagLayout());

        zerozero.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        zerozero.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        zerozero.setText("0,0");
        zerozero.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        zerozero.setPreferredSize(new java.awt.Dimension(75, 75));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        gamePanel.add(zerozero, gridBagConstraints);

        zeroone.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        zeroone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        zeroone.setText("0,1");
        zeroone.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        zeroone.setPreferredSize(new java.awt.Dimension(75, 75));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 0, 0);
        gamePanel.add(zeroone, gridBagConstraints);

        zerothree.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        zerothree.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        zerothree.setText("0,3");
        zerothree.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        zerothree.setPreferredSize(new java.awt.Dimension(75, 75));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 0, 2);
        gamePanel.add(zerothree, gridBagConstraints);

        onezero.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        onezero.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        onezero.setText("1,0");
        onezero.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        onezero.setName(""); // NOI18N
        onezero.setPreferredSize(new java.awt.Dimension(75, 75));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 2, 0, 0);
        gamePanel.add(onezero, gridBagConstraints);

        oneone.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        oneone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        oneone.setText("1,1");
        oneone.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        oneone.setPreferredSize(new java.awt.Dimension(75, 75));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        gamePanel.add(oneone, gridBagConstraints);

        onetwo.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        onetwo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        onetwo.setText("1,2");
        onetwo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        onetwo.setPreferredSize(new java.awt.Dimension(75, 75));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        gamePanel.add(onetwo, gridBagConstraints);

        twothree.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        twothree.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        twothree.setText("2,3");
        twothree.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        twothree.setPreferredSize(new java.awt.Dimension(75, 75));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 2);
        gamePanel.add(twothree, gridBagConstraints);

        zerotwo.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        zerotwo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        zerotwo.setText("0,2");
        zerotwo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        zerotwo.setPreferredSize(new java.awt.Dimension(75, 75));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 0, 0);
        gamePanel.add(zerotwo, gridBagConstraints);

        twozero.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        twozero.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        twozero.setText("2,0");
        twozero.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        twozero.setPreferredSize(new java.awt.Dimension(75, 75));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 2, 0, 0);
        gamePanel.add(twozero, gridBagConstraints);

        twoone.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        twoone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        twoone.setText("2,1");
        twoone.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        twoone.setPreferredSize(new java.awt.Dimension(75, 75));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        gamePanel.add(twoone, gridBagConstraints);

        twotwo.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        twotwo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        twotwo.setText("2,2");
        twotwo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        twotwo.setPreferredSize(new java.awt.Dimension(75, 75));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        gamePanel.add(twotwo, gridBagConstraints);

        onethree.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        onethree.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        onethree.setText("1,3");
        onethree.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        onethree.setPreferredSize(new java.awt.Dimension(75, 75));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 2);
        gamePanel.add(onethree, gridBagConstraints);

        threezero.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        threezero.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        threezero.setText("3,0");
        threezero.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        threezero.setPreferredSize(new java.awt.Dimension(75, 75));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 2, 2, 0);
        gamePanel.add(threezero, gridBagConstraints);

        threeone.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        threeone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        threeone.setText("3,1");
        threeone.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        threeone.setPreferredSize(new java.awt.Dimension(75, 75));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 2, 0);
        gamePanel.add(threeone, gridBagConstraints);

        threetwo.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        threetwo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        threetwo.setText("3,2");
        threetwo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        threetwo.setPreferredSize(new java.awt.Dimension(75, 75));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 2, 0);
        gamePanel.add(threetwo, gridBagConstraints);

        threethree.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        threethree.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        threethree.setText("3,3");
        threethree.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        threethree.setPreferredSize(new java.awt.Dimension(75, 75));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 2, 2);
        gamePanel.add(threethree, gridBagConstraints);

        newGame.setText("new game");
        newGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });

        currentScoreLabel.setText("current score:");

        theScore.setText("<score>");

        controls.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        controls.setPreferredSize(new java.awt.Dimension(188, 188));
        controls.setLayout(new java.awt.GridBagLayout());

        up.setMnemonic('w');
        up.setText("↑");
        up.setToolTipText("");
        up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        controls.add(up, gridBagConstraints);

        down.setMnemonic('s');
        down.setText("↓");
        down.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        controls.add(down, gridBagConstraints);

        left.setMnemonic('a');
        left.setText("←");
        left.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        controls.add(left, gridBagConstraints);

        right.setMnemonic('d');
        right.setText("→");
        right.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rightActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        controls.add(right, gridBagConstraints);

        javax.swing.GroupLayout parentLayout = new javax.swing.GroupLayout(parent);
        parent.setLayout(parentLayout);
        parentLayout.setHorizontalGroup(
            parentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(parentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(parentLayout.createSequentialGroup()
                        .addGroup(parentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(newGame)
                            .addGroup(parentLayout.createSequentialGroup()
                                .addComponent(currentScoreLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(theScore)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(controls, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        parentLayout.setVerticalGroup(
            parentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(parentLayout.createSequentialGroup()
                        .addComponent(newGame)
                        .addGap(18, 18, 18)
                        .addGroup(parentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(currentScoreLabel)
                            .addComponent(theScore))
                        .addGap(18, 18, 18)
                        .addComponent(controls, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(gamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameActionPerformed
        this.setVisible(false);
        new TTFE().setVisible(true);
        //updateLabel.run(); // runs thread to update graphics
    }//GEN-LAST:event_newGameActionPerformed

    private void upActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upActionPerformed
        this.pressedUp();
    }//GEN-LAST:event_upActionPerformed

    private void downActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downActionPerformed
        this.pressedDown();
    }//GEN-LAST:event_downActionPerformed

    private void leftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftActionPerformed
        this.pressedLeft();
    }//GEN-LAST:event_leftActionPerformed

    private void rightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightActionPerformed
        this.pressedRight();
    }//GEN-LAST:event_rightActionPerformed

    private void parentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_parentKeyPressed

    }//GEN-LAST:event_parentKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TTFE.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TTFE.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TTFE.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TTFE.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TTFE().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controls;
    private javax.swing.JLabel currentScoreLabel;
    private javax.swing.JButton down;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JButton left;
    private javax.swing.JButton newGame;
    private javax.swing.JLabel oneone;
    private javax.swing.JLabel onethree;
    private javax.swing.JLabel onetwo;
    private javax.swing.JLabel onezero;
    private javax.swing.JPanel parent;
    private javax.swing.JButton right;
    private javax.swing.JLabel theScore;
    private javax.swing.JLabel threeone;
    private javax.swing.JLabel threethree;
    private javax.swing.JLabel threetwo;
    private javax.swing.JLabel threezero;
    private javax.swing.JLabel twoone;
    private javax.swing.JLabel twothree;
    private javax.swing.JLabel twotwo;
    private javax.swing.JLabel twozero;
    private javax.swing.JButton up;
    private javax.swing.JLabel zeroone;
    private javax.swing.JLabel zerothree;
    private javax.swing.JLabel zerotwo;
    private javax.swing.JLabel zerozero;
    // End of variables declaration//GEN-END:variables

}
