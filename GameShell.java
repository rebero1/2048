
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;


public class GameShell extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1L;
     int keypressed=0;
	Font font;
	int[][] array;
	int sizeArr =4;
  	private boolean beginning = true;
  	int [][] temp_array;



	/** Constructor for initializing**/

	public GameShell() {
		addKeyListener(this);
		setFocusable(true);
		font = new Font("Arial", Font.BOLD, 25);
		setBackground(getBackground());
		array = new int[sizeArr][sizeArr];
		temp_array=new  int[sizeArr][sizeArr];


	}

	@Override
	public void paintComponent(Graphics g2) {

		if (beginning) {
			addToarray();
			addToarray();
			beginning = false;
		}
		Graphics2D g = (Graphics2D) g2;
		super.paintComponent(g);
		g.setFont(font);
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 4; i++) {
				g.setColor(colorRectangle(array[i][j]));
				g.fillRoundRect((7 + (getWidth() / 4) * j), (7 + (getHeight() / 4) * i),
						getWidth() / 4 - 15, getHeight() / 4 - 15, 14, 14);

				int x = (7 + (getWidth() / 4) * j) + ((getWidth() / 4 - 10) / 2 - 12-(Integer.toString(array[i][j]).length()*3));
				int y = (7 + (getHeight() / 4) * i) + ((getHeight() / 4 - 10) / 2 + 10);
				if (array[i][j] == 0)
					continue;
				else {
					g.setColor(Color.black);
					g.drawString(Integer.toString(array[i][j]), x, y);
				}
			}
		}
	}




	/** Colors for the board**/

	private Color colorRectangle(int number) {

 			switch (number) {
				case 2:    return new Color(0xEEE7D2);
				case 4:    return new Color(0xEDD5C1);
				case 8:    return new Color(0xF2A37D);
				case 16:   return new Color(0xF5BC8D);
				case 32:   return new Color(0xF66659);
				case 64:   return new Color(0xf65e3b);
				case 128:  return new Color(0xEDC893);
				case 256:  return new Color(0xBAED2B);
				case 512:  return new Color(0xED65EA);
				case 1024: return new Color(0xEDAF4C);
				case 2048:
				{

winning();

					return new Color(0xB0BEED);
				}
				default:
				return 	new Color(0x6ECDBD);
		}
	}

	public void keyTyped(KeyEvent e) {
	}



	/** Key listerner**/
	public void keyPressed(KeyEvent e) {
 int displayed=0;
 		keypressed++;
		int code = e.getKeyCode();
		String valid_key="a d s q w r";


		if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
			if(code==KeyEvent.VK_LEFT) {
				System.out.println("Key Pressed: LEFT ARROW, Valid");
				displayed+=1;
			}


			repaint();
 			fill_temp();
			runningSumLeft();

			addToarray();
		}
		if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
			if(code==KeyEvent.VK_RIGHT) {
				System.out.println("Key Pressed: RIGHT ARROW, Valid");
				displayed++;
			}
			displayed++;
			repaint();
			fill_temp();
			runningSumRight();
  				addToarray();
		}
		if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
			if(code==KeyEvent.VK_UP) {
				System.out.println("Key Pressed: UP ARROW, Valid");
				displayed++;
			}
			displayed++;
			repaint();
			fill_temp();

			runningSumUp();
  				addToarray();
		}
		if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
			if(code==KeyEvent.VK_DOWN) {
				System.out.println("Key Pressed: DOWN ARROW, Valid");
				displayed++;
			}
			repaint();
			fill_temp();

			runningSumDown();
  				addToarray();
		}
		if (code == KeyEvent.VK_R ) {
restart();
		}

		if (code == KeyEvent.VK_Q ) {
shutdown();

		}
		if(displayed==0)
 		System.out.println("Key Pressed:"+e.getKeyChar()+","+(valid_key.contains(""+e.getKeyChar())?"Key Valid":"Key Not Valid"));
		System.out.println("The maximum value:"+maxfunc());
		System.out.println("The number of moves:"+keypressed);

	}

	public void keyReleased(KeyEvent e) {
	}
/** Generating elements in a random way with probabilities**/

	public int randomGenerator() {
		Random rand = new Random();
		double probab = rand.nextGaussian();
		if (probab >= -1.28 && probab <= 1.28) {
			return 2;
		} else {
			return 4;
		}
	}

	/** Adding Elements in array**/

	public void addToarray() {


 		boolean isempty = true;
		int location_x = -1;
		int location_y = -1;
		int number = -1;
		int zero=0;
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				if(array[i][j]==0)
					zero++;

int n;


		if(zero==0) {
			Object[] options = {"Restart",
					"Close!"};
			 n = JOptionPane.showOptionDialog(new JFrame(),
					"Game is over"+"\n"+"Number of key pressed:"+keypressed,
					"2048",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,     //do not use a custom Icon
					options,  //the titles of buttons
					options[0]);

if(n!=0)
 System.exit(0);
else{
	for (int i = 0; i < 4; i++)
		for (int j = 0; j <4 ; j++)
			array[i][j]=0;



 	keypressed=0;

  beginning=true;

	repaint();




}
 return;
		}

		if(array_same() && !beginning)
			return;

		while (isempty) {
			location_x = new Random().nextInt(4);
			location_y = new Random().nextInt(4);

			if (array[location_x][location_y] == 0) {
				number = randomGenerator();
				array[location_x][location_y] = number;
				isempty = false;
			}
		}
 	}


	/** Performing the sum on the left**/
	public  void runningSumLeft() {
		for (int row = 0; row < array.length; row++) {
			int countColumn =0;
			int previous = 0;
			for (int column = 0; column < array[row].length; column++) {
				if(array[row][column]!=0){
					if(previous==0) {
 						previous = array[row][column];
						array[row][column]=0;
					}
					else {
						if (previous == array[row][column]) {
 							array[row][countColumn] = array[row][column] * 2;
 							array[row][column] =0;
							countColumn++;
							previous = 0;
						} else {
							array[row][countColumn] = previous;
 							countColumn++;
							previous = array[row][column];
						}
					}
				}
			}
			if(previous!=0){
				array[row][countColumn]=previous;
			}
		}
	}


	/** Performing the sum on the Right**/

	public  void runningSumRight() {
		for (int row = 0; row < array.length; row++) {
			int countColumn = array[row].length-1;
			int previous = 0;
			for (int column = array[row].length-1; column >=0; column--) {
				if(array[row][column]!=0){
					if(previous==0) {
 						previous = array[row][column];
						array[row][column]=0;
					}
					else {
						if (previous == array[row][column]) {
 							array[row][countColumn] = array[row][column] * 2;
 							array[row][column] =0;
							countColumn--;
							previous = 0;
						} else {
							array[row][countColumn] = previous;
 							countColumn--;
							previous = array[row][column];
							array[row][column] =0;
						}
					}
				}
			}
			if(previous!=0){
				array[row][countColumn]=previous;
			}
		}
	}


	/** Performing the sum on the Up**/

	public  void runningSumUp() {
		for(int column=0; column<array[0].length; column++) {
			int countRow = 0;
			int previous = 0;
			for(int row=0; row<array.length; row++) {
				if(array[row][column]!=0){
					if(previous==0) {

						previous = array[row][column];
						array[row][column]=0;
					}
					else {
						if (previous == array[row][column]) {

							array[countRow][column] = array[row][column] * 2;

							array[row][column] =0;
							countRow++;
							previous = 0;
						}
						else {
							array[countRow][column] = previous;
							countRow++;
							previous = array[row][column];
							array[row][column] =0;
						}
					}
				}
			}
			if(previous!=0){
				array[countRow][column]=previous;
			}
		}
	}


	/** Performing the sum on the Down**/

	public  void runningSumDown() {
		for(int column=0; column<array[0].length; column++) {
			int countRow = array[0].length-1;
			int previous = 0;
			for(int row=array.length-1; row>=0; row--) {
				if(array[row][column]!=0){
					if(previous==0) {
						previous = array[row][column];
						array[row][column]=0;
					}
					else {
						if (previous == array[row][column]) {
							array[countRow][column] = array[row][column] * 2;
							array[row][column] =0;
							countRow--;
							previous = 0;
						}
						else {
							array[countRow][column] = previous;
							countRow--;
							previous = array[row][column];
							array[row][column] =0;
						}
					}
				}
			}
			if(previous!=0){
				array[countRow][column]=previous;
			}
		}
	}

/** quit the game**/
public void shutdown(){


	int response=	JOptionPane.showConfirmDialog(new JFrame().getContentPane(),"Do you want to quit the game?");


	if(response==0){


	System.exit(0);

	}
}



/** used to check if we can add an element or not **/
public boolean array_same(){





	for (int i = 0; i <4 ; i++) {
		for (int j = 0; j < 4; j++) {
			if (temp_array[i][j] != array[i][j])
				return false;
		}
	}




	return true;
}





	/** Called when the user wins**/

public void winning(){

		Object[] options = {
				"Close!"};
		int n = JOptionPane.showOptionDialog(new JFrame(),
				"You WON!!!!",
				"2048",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,     //do not use a custom Icon
				options,  //the titles of buttons
				options[0]);

			System.exit(0);


	}



	public int maxfunc(){
		int maxValue = array[0][0];
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 4; i++) {
				if (array[j][i] > maxValue) {
					maxValue = array[j][i];
				}
			}
		}
		return maxValue;
	}

	public void fill_temp(){

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				temp_array[i][j]=array[i][j];
	}

	/** restarting the game **/


	public void restart(){

beginning=true;
	int response=	JOptionPane.showConfirmDialog(new JFrame().getContentPane(),"Do you want to restart the game?");


	if(response==0){

 		for (int i = 0; i < 4; i++)
			for (int j = 0; j <4 ; j++)
				array[i][j]=0;




 			keypressed=0;


	repaint();

		}
 	}

	}

