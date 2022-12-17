import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class StartedGame extends Composite {

    public static final int LEFT_BAR_WIDTH = 150; //in pixels

    private BufferedImage img;
    private int number_of_blocks;
    private int number_of_moves;
    private JLabel number_of_moves_label;
    private JLabel timerLabel;
    int mili, sec, min;
    boolean state;
    private JButton restart_button;
    private JButton options_button;
    private JButton quit_button;
    private ArrayList<JButton> tile_buttons;
    private ArrayList<Integer> tiles_order;
    private JButton last_pressed_button;

    public StartedGame(String name, JFrame window, GuiHandler gui_handler, BufferedImage img, int number_of_blocks) {
        super(name, window, gui_handler);
        this.img = img;
        this.number_of_blocks = number_of_blocks;
        this.number_of_moves = 0;
        this.number_of_moves_label = null;

        this.mili = 0;
        this.sec = 0;
        this.min = 0;
        this.timerLabel = null;

        this.restart_button = new JButton("Restart");
        this.restart_button.setForeground(Color.WHITE);
        this.restart_button.setBackground(new Color(151, 0, 222));

        this.options_button = new JButton("Options");
        this.options_button.setForeground(Color.WHITE);
        this.options_button.setBackground(new Color(151, 0, 222));

        this.quit_button = new JButton("Quit");
        this.quit_button.setForeground(Color.WHITE);
        this.quit_button.setBackground(new Color(151, 0, 222));

        this.tile_buttons = null;
        this.tiles_order = null;
        this.last_pressed_button = null;

        this.timer();

        this.setLayout(new BorderLayout(0, 0));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
    }

    //Loads GUI sections of this composite.
    public void loadSections() {
        this.loadLeftBarSection();
        this.loadTilesSection();
    }

    //Loads labels, buttons of game's left bar and adds it to the composite.
    private void loadLeftBarSection() {
        JPanel left_bar_section = new JPanel();
        left_bar_section.setName("LEFT_BAR");
        left_bar_section.setLayout(new BoxLayout(left_bar_section, BoxLayout.PAGE_AXIS));
        left_bar_section.setBorder(BorderFactory.createMatteBorder(0,0, 0, 5, Color.BLACK));
        left_bar_section.setPreferredSize(new Dimension(this.LEFT_BAR_WIDTH, 2));

        //Moves label
        Font font = new Font("Arial", Font.BOLD,20);
        JLabel moves_title = new JLabel("Moves");
        moves_title.setForeground(new Color(151, 0, 222));
        moves_title.setFont(font);
        moves_title.setAlignmentX(CENTER_ALIGNMENT);

        this.number_of_moves_label = new JLabel(Integer.toString(this.number_of_moves));
        this.number_of_moves_label.setForeground(new Color(0, 9, 222));
        this.number_of_moves_label.setFont(font);
        this.number_of_moves_label.setAlignmentX(CENTER_ALIGNMENT);

        //Timer label
        JLabel timerLabelTitle = new JLabel("Time");
        timerLabelTitle.setForeground(new Color(151, 0, 222));
        timerLabelTitle.setFont(font);
        timerLabelTitle.setAlignmentX(CENTER_ALIGNMENT);

        //timer digit
        this.timerLabel = new JLabel(this.min+":"+this.sec+":"+this.mili);
        this.timerLabel.setForeground(new Color(0, 9, 222));
        this.timerLabel.setFont(font);
        this.timerLabel.setAlignmentX(CENTER_ALIGNMENT);

        this.restart_button.setAlignmentX(CENTER_ALIGNMENT);
        this.restart_button.addActionListener(this);
        this.options_button.setAlignmentX(CENTER_ALIGNMENT);
        this.options_button.addActionListener(this);
        this.quit_button.setAlignmentX(CENTER_ALIGNMENT);
        this.quit_button.addActionListener(this);

        left_bar_section.add(Box.createRigidArea(new Dimension(LEFT_BAR_WIDTH, 30)));
        left_bar_section.add(moves_title);
        left_bar_section.add(Box.createRigidArea(new Dimension(LEFT_BAR_WIDTH, 10)));
        left_bar_section.add(this.number_of_moves_label);
        left_bar_section.add(Box.createRigidArea(new Dimension(LEFT_BAR_WIDTH, 30)));
        left_bar_section.add(timerLabelTitle);
        left_bar_section.add(Box.createRigidArea(new Dimension(LEFT_BAR_WIDTH, 10)));
        left_bar_section.add(this.timerLabel);
        left_bar_section.add(Box.createVerticalGlue());
        left_bar_section.add(this.restart_button);
        left_bar_section.add(Box.createRigidArea(new Dimension(LEFT_BAR_WIDTH, 10)));
        left_bar_section.add(this.options_button);
        left_bar_section.add(Box.createRigidArea(new Dimension(LEFT_BAR_WIDTH, 10)));
        left_bar_section.add(this.quit_button);
        left_bar_section.add(Box.createRigidArea(new Dimension(LEFT_BAR_WIDTH, 30)));

        this.add(left_bar_section, BorderLayout.LINE_START);
    }

    //Loads tiles which are represented by JButtons and adds them to the composite.
    //It crops parts of the image properly to set icons of buttons. A button with icon forms tile.
    //Firstly, position of every button is saved in the order list (index = right position).
    //Then icons and positions are shuffled.
    private void loadTilesSection() {
        //Tiles container for appropriate tiles centering.
        JPanel tiles_container_section = new JPanel();
        tiles_container_section.setName("TILES_CONTAINER");
        tiles_container_section.setBorder(BorderFactory.createLineBorder(Color.darkGray, 5));

        JPanel tiles_section = new JPanel();
        tiles_section.setName("TILES");
        tiles_section.setLayout(new GridLayout(this.number_of_blocks, this.number_of_blocks));
        tiles_section.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        tiles_section.setMaximumSize(new Dimension(img.getWidth(), img.getHeight()));
        tiles_section.setMinimumSize(new Dimension(img.getWidth(), img.getHeight()));

        int current_block_pos_x, current_block_pos_y, id_iterator = 0;
        int block_size = this.img.getHeight() / this.number_of_blocks;
        this.tiles_order = new ArrayList<>();
        this.tile_buttons = new ArrayList<>();

        for(int i = 0; i < this.number_of_blocks; i++) {
            for (int j = 0; j < this.number_of_blocks; j++) {

                JButton temp_button = new JButton();
                temp_button.addActionListener(this);
                temp_button.setPreferredSize(new Dimension(block_size, block_size));
                temp_button.setMinimumSize(new Dimension(block_size, block_size));
                temp_button.setMaximumSize(new Dimension(block_size, block_size));
                temp_button.setName(Integer.toString(id_iterator));

                //Saving tile's ID for puzzle correctness check.
                this.tiles_order.add(id_iterator++);

                //Tile coordinates on the image.
                current_block_pos_x = j * block_size;
                current_block_pos_y = i * block_size;

                //Crops the image to paint one tile.
                BufferedImage cropped_img = this.img.getSubimage(current_block_pos_x, current_block_pos_y, block_size, block_size);
                temp_button.setIcon(new ImageIcon(cropped_img));

                tile_buttons.add(temp_button);
                tiles_section.add(temp_button);
            }
        }

        this.shuffleTiles();

        tiles_container_section.add(tiles_section);
        this.add(tiles_container_section, BorderLayout.CENTER);

        this.window.pack();
    }

    //Loads win screen and adds it to the composite.
    private void loadWinSection() {
        JPanel win_section = new JPanel();
        win_section.setName("WIN");
        win_section.setLayout(new BoxLayout(win_section, BoxLayout.PAGE_AXIS));
        win_section.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        win_section.setBackground(Color.WHITE);

        JLabel win_message = new JLabel("YOU WON!");
        win_message.setForeground(new Color(151, 0, 222));
        Font font = new Font("Arial", Font.BOLD,30);
        win_message.setFont(font);
        win_message.setAlignmentX(CENTER_ALIGNMENT);

        win_section.add(Box.createVerticalGlue());
        win_section.add(win_message);
        win_section.add(Box.createVerticalGlue());

        this.add(win_section, BorderLayout.CENTER);
    }

    //Removes tiles and shows win message.
    private void showWinMessage() {
        this.removeSection("TILES_CONTAINER");
        this.loadWinSection();
        this.window.validate();
    }

    //Updates moves label with actual amount of moves.
    private void updateMovesLabel() {
        number_of_moves_label.setText(Integer.toString(this.number_of_moves));
    }

    //Checks if tiles are positioned in right order.
    private boolean checkIfGameIsWon() {
        for (int i = 0; i < this.tiles_order.size(); i++) {
            if (i != this.tiles_order.get(i)) {
                return false;
            }
        }
        return true;
    }

    //Randomly shuffles all of the tiles.
    private void shuffleTiles() {
        Random random = new Random();

        for(int i = 0; i < this.tile_buttons.size(); i++) {

            JButton current_tile_button = this.tile_buttons.get(i);
            int random_tile_number = random.nextInt(this.number_of_blocks);
            JButton random_tile_button = this.tile_buttons.get(random_tile_number);

            //Change icons.
            Icon temp_icon = current_tile_button.getIcon();
            current_tile_button.setIcon(random_tile_button.getIcon());
            random_tile_button.setIcon(temp_icon);

            //Change positions in order list.
            int index_of_current_tile_button = this.tiles_order.indexOf(Integer.parseInt(current_tile_button.getName()));
            int index_of_random_tile_button = this.tiles_order.indexOf(Integer.parseInt(random_tile_button.getName()));
            this.tiles_order.set(index_of_current_tile_button, Integer.parseInt(random_tile_button.getName()));
            this.tiles_order.set(index_of_random_tile_button, Integer.parseInt(current_tile_button.getName()));
        }
    }

    //Restars the game with same configuration.
    private void restartGame() {
        this.removeSection("TILES_CONTAINER");
        this.loadTilesSection();
        this.number_of_moves = 0;
        this.updateMovesLabel();
        this.timerResest();
        this.timer();
    }

    //timer
    public void timer(){
        state = true;

        Thread t = new Thread(){
            public void run(){
                for( ; ; ){
                    if(state == true){
                        try {
                            sleep(1);

                            if(mili>1000){
                                mili = 0;
                                sec++;
                            }

                            if(sec>60){
                                mili=0;
                                sec=0;
                                min++;
                            }

                            mili++;
                            timerLabel.setText(min+":"+sec+":"+mili);

                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                    else{
                        break;
                    }
                }
            }
        };
        t.start();
    }

    public void timerResest(){
        state = false;
        mili = 0;
        sec = 0;
        min = 0;

        timerLabel.setText(min+":"+sec+":"+mili);
    }

    public void timerWin() {
        state = false;
    }

    //Handles events generated by buttons.
    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();
        if(source == this.restart_button) {
            this.restartGame();
            return;
        }
        if(source == this.options_button) {
            this.gui_handler.showComposite("OPTIONS");
            this.gui_handler.deleteComposite("STARTED_GAME");
            return;
        }
        if(source == this.quit_button) {
            this.gui_handler.showComposite("MAIN_MENU");
            this.gui_handler.deleteComposite("STARTED_GAME");
            return;
        }

        ///////////////TILES-MOVEMENT-HANDLING///////////////

        JButton current_button = (JButton) e.getSource();

        if(this.last_pressed_button == null) {
            this.last_pressed_button = current_button;
        }
        else {
            //Checks if choosen tiles are next to each other.
            if ((Math.abs(Integer.parseInt(this.last_pressed_button.getName()) - Integer.parseInt(current_button.getName())) == 1)
                    || ((Math.abs(Integer.parseInt(this.last_pressed_button.getName()) - Integer.parseInt(current_button.getName()))) == this.number_of_blocks)) {

                //Change icons.
                Icon temp_icon = this.last_pressed_button.getIcon();
                this.last_pressed_button.setIcon(current_button.getIcon());
                current_button.setIcon(temp_icon);

                //Change positions in order list.
                int index_of_last_button = this.tiles_order.indexOf(Integer.parseInt(this.last_pressed_button.getName()));
                int index_of_current_button = this.tiles_order.indexOf(Integer.parseInt(current_button.getName()));
                this.tiles_order.set(index_of_last_button, Integer.parseInt(current_button.getName()));
                this.tiles_order.set(index_of_current_button, Integer.parseInt(this.last_pressed_button.getName()));

                this.number_of_moves++;
                this.updateMovesLabel();
            }

            this.last_pressed_button = null;
            if(this.checkIfGameIsWon()) {
                this.showWinMessage();
                this.timerWin();
            }
        }
    }
}
