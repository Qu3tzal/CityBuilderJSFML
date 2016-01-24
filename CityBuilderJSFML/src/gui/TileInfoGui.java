package gui;

import java.util.ArrayList;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import graphics.FontManager;
import graphics.Tile;
import world.Building;
import world.Resource;
import world.ResourcesMap;
import world.ResourcesStack;

public class TileInfoGui implements Drawable {

	protected String infoString;
	protected Text infoText;
	protected Vector2i position;
	protected ResourcesMap resourceMap;
	protected TileSelector tileSelector;
	protected ArrayList<ArrayList<Tile>> tileMap;
	protected Tile actualTile;
	protected Building building;
	protected RectangleShape rectangleShape;
	
	public TileInfoGui(ArrayList<ArrayList<Tile>> tilemap, FontManager fontmanager) {
		
		this.tileMap = tilemap;
		this.infoString = new String();
		
		// we instanciate the Text
		this.infoText = new Text();
		this.infoText.setFont(fontmanager.get(FontManager.FontID.CAVIAR_DREAM));
		this.infoText.setString(this.infoString);
		this.infoText.setCharacterSize(12);
		this.infoText.setColor(Color.WHITE);
		
		// we instanciate the RectangleShape
		this.rectangleShape = new RectangleShape();
		this.rectangleShape.setFillColor(new Color(0,0,25,100));
		
	}
	/**
	 * set the string of resources on the tile or batiment
	 * @param resourcemap : to get resources on the tile or batiment
	 * @param tileselector : to get the position of the mouse
	 * @param buildings : to get buildings on the map
	 */
	public void update(ResourcesMap resourcemap, TileSelector tileselector,ArrayList<Building> buildings) {
		this.tileSelector = tileselector;
		this.resourceMap = resourcemap;
		this.infoString = "";
		
		this.position = new Vector2i(this.tileSelector.selectedTile.x,this.tileSelector.selectedTile.y);
		this.actualTile = this.tileMap.get(position.y).get(position.x);
		
		// if it's the ground with no buildings
		if(this.actualTile.getTileType().equals(Tile.TileType.TERRAIN_GRASS)) {
			this.infoString += "TILE_TYPE :" + this.actualTile.getTileType().toString() + " \n";
			for(Resource.ResourceType resource : Resource.ResourceType.values()) {
				if(resourcemap.getResources(this.position.x, this.position.y).get(resource) > 0) {
					this.infoString += resource.toString() + " : " + resourcemap.getResources(this.position.x, this.position.y).get(resource) + "\n";
				}
			}
		
		}
		// if it's a buildings
		else {
			for(Building b : buildings) {
				if(b.getHitbox().contains(position)) {
					this.building = b;
					break;
				}
			}
			this.infoString += "BUILDING_NAME :" + this.building.getType().toString() + "[" + this.building.getId() + "] \n";
			// Get the resources available for the building.
			ResourcesStack availableResources = new ResourcesStack();
			
			for(int x = this.building.getHitbox().left ; x < this.building.getHitbox().left + this.building.getHitbox().width ; ++x) {
				for(int y = this.building.getHitbox().top ; y < this.building.getHitbox().top + this.building.getHitbox().height ; ++y) {
					availableResources.add(resourcemap.getResources(new Vector2i(x, y)));
				}
			}
			for(Resource.ResourceType resource : Resource.ResourceType.values()) {
				if(availableResources.get(resource) > 0) {
					this.infoString += resource.toString() + " : " + availableResources.get(resource) + "\n";
				}
			}
			// Tile info
			this.infoString += "TILE_TYPE :" + this.actualTile.getTileType().toString() + " \n";
			for(Resource.ResourceType resource : Resource.ResourceType.values()) {
				if(resourcemap.getResources(this.position.x, this.position.y).get(resource) > 0) {
					this.infoString += resource.toString() + " : " + resourcemap.getResources(this.position.x, this.position.y).get(resource) + "\n";
				}
			}
		}
		// we set the text
		this.infoText.setString(infoString);
		this.infoText.setPosition(this.position.x*16 +20, this.position.y*16 +20);
		
		// we set the rectangle
		this.rectangleShape.setPosition(this.position.x *16, this.position.y*16);
		this.rectangleShape.setSize(new Vector2f(this.infoText.getGlobalBounds().width + 30,this.infoText.getGlobalBounds().height + 30));
		
	} 
	
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		
		target.draw(this.rectangleShape, states);
		target.draw(this.infoText, states);
		
	}
}
