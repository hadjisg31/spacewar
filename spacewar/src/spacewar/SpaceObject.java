	package spacewar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Random;

import javax.swing.ImageIcon;

public class SpaceObject implements ImageObserver, GameDefaults  {
	
	private Image _img;			// holder for the image object from icon 
	private ImageIcon _imgIcon;	// Image icon from object/file image
	private int _xPos;			// screen X position
	private int _yPos;			// screen Y position
	private int _scoreVal;		// assign a score value when hit by missile, used mostly by Enemy object
	private int _scnPixHeight;	// pixel size of object, height
	private int _scnPixWidth;	// pixel size of object, width
	private int _numLives=0;	// Default game lives allotted to a game object, ship or enemy
	
	
	// constructor to accept image filename/path
	SpaceObject(String _fname) {
		
		_imgIcon = new ImageIcon(_fname);
		_img = _imgIcon.getImage();
	
	}

	// constructor overload with including X,Y positions and intended size of image
	SpaceObject(String _fname, int xPos, int yPos, int width, int height) {
		
		_imgIcon = new ImageIcon(_fname);
		_img = _imgIcon.getImage();
		
		this._xPos = xPos;
		this._yPos = yPos;
		this._scnPixHeight = height;
		this._scnPixWidth = width;
	
	}
	
	
	public Image get_image() {
		return _img;
	}

	public int get_xPos() {
		return _xPos;
	}

	public void set_xPos(int _xPos) {
		this._xPos = _xPos;
	}

	public int get_yPos() {
		return _yPos;
	}

	public void set_yPos(int _yPos) {
		this._yPos = _yPos;
	}

	public int get_scoreVal() {
		return _scoreVal;
	}

	public void set_scoreVal(int _scoreVal) {
		this._scoreVal = _scoreVal;
	}

	/*
	 * Draws specific image on screen with parameters X,Y and image height and with
	 * Background color is defaulted to black for now.
	 */
   public void draw(Graphics gr)
    {
		gr.drawImage(get_image(), get_xPos(), get_yPos(), _scnPixWidth, _scnPixHeight, SCN_OBJ_BG_COLOR, this);
	}

	public int get_scnPixHeight() {
		return _scnPixHeight;
	}
	
	public void set_scnPixHeight(int _scnPixHeight) {
		this._scnPixHeight = _scnPixHeight;
	}
	
	public int get_scnPixWidth() {
		return _scnPixWidth;
	}
	
	public void set_scnPixWidth(int _scnPixWidth) {
		this._scnPixWidth = _scnPixWidth;
	}

	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

	public int get_numLives() {
		return _numLives;
	}

	public void set_numLives(int _numLives) {
		this._numLives = _numLives;
	}

	
   
}
