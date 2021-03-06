package gui;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;

import graphics.FontManager;
import graphics.TextureManager;

/*
 * A simple checkbox class
 */
public class CheckBox implements Drawable{
	protected IntRect hitbox;
	protected boolean checkFlag;
	protected Sprite checkedSprite;
	protected Sprite noCheckedSprite;
	protected Text text;
	protected int value;
	
	/**
	 * Constructor of the checkbox
	 * @param x : the position x of the checkbox
	 * @param y : the position y of the checkbox
	 * @param loader : to get the texture of the checkbox
	 */
	public CheckBox(int x, int y, TextureManager textures, FontManager fonts, String text, int value) {
		this.hitbox = new IntRect(x, y, 16, 16);
		this.text = new Text();
		this.text.setFont(fonts.get(FontManager.FontID.VCR_MONO));
		this.text.setCharacterSize(16);
		this.text.setString(text);
		this.text.setColor(Color.WHITE);
		this.text.setPosition(this.hitbox.left + 20, this.hitbox.top - 2);
		
		this.checkedSprite = new Sprite();
		this.checkedSprite.setTexture(textures.get(TextureManager.TextureID.CHECKBOX_TEXTURE));
		this.checkedSprite.setTextureRect(new IntRect(16, 0, 16, 16));
		this.checkedSprite.setPosition(x, y);
		
		this.noCheckedSprite = new Sprite();
		this.noCheckedSprite.setTexture(textures.get(TextureManager.TextureID.CHECKBOX_TEXTURE));
		this.noCheckedSprite.setTextureRect(new IntRect(0, 0, 16, 16));
		this.noCheckedSprite.setPosition(x, y);
		
		this.value = value;
	}
	
	/**
	 * Returns the value of the checkbox.
	 * @return the value.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * set the checkflag of the checkbox
	 * @param b : new states of the checkbox
	 */
	public void setChecked(boolean b) {
		this.checkFlag = b;
	}
	
	/**
	 * return the checkflag
	 * @return boolean : the checkflag
	 */
	public boolean isChecked() {
		return this.checkFlag;
	}
	
	/**
	 * return the hitbox of the checkbox
	 * @return IntRect : the hitbox of the checkbox
	 */
	public IntRect getHitbox() {
		return this.hitbox;
	}
	
	/**
	 * Handles the event.
	 * @param event : the JSFML event to handle
	 */
	public void handleEvent(Event e) {
		if(e.type == Event.Type.MOUSE_BUTTON_RELEASED && e.asMouseButtonEvent().button == Mouse.Button.LEFT && this.getHitbox().contains(e.asMouseEvent().position)) {
			this.setChecked(!this.isChecked());
		}
	}

	@Override
	public void draw(RenderTarget target, RenderStates states) {
		if(this.checkFlag) {
			target.draw(this.checkedSprite);
		}
		else {
			target.draw(this.noCheckedSprite);
		}
		target.draw(this.text);
		
	}
}
