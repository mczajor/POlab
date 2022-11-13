package agh.ics.oop;
import java.util.ArrayList;

public class Animal implements IMapElement {
    AbstractWorldMap map;
    ArrayList<IPositionChangeObserver> observers = new ArrayList<>();
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    public Animal(AbstractWorldMap map){
        this.map = map;
        this.position = new Vector2d(0,0);
    }
    public Animal(AbstractWorldMap map, Vector2d initialPosition){
        this.position = initialPosition;
        this.map = map;
    }
    public String toString(){
        return switch(this.orientation){
            case NORTH -> "N";
            case SOUTH -> "S";
            case WEST -> "W";
            case EAST -> "E";
        };
    }
    public MapDirection getOrientation(){
        return this.orientation;
    }
    public Vector2d getPosition(){
        return this.position;
    }
    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }
    public void move(MoveDirection direction){
        switch (direction) {
            case RIGHT -> this.orientation = this.orientation.next(); // rotate right
            case LEFT -> this.orientation = this.orientation.previous(); //rotate left
            default -> {
                Vector2d unitVector = orientation.toUnitVector();
                // need to go opposite of unitvector if direction backwart
                if (direction == MoveDirection.BACKWARD){
                    unitVector = unitVector.opposite();
                }
                // If new position is viable change the vector for a new one
                Vector2d newPosition = this.position.add(unitVector);
                if (map.canMoveTo(newPosition)){
                    Vector2d oldPosition = this.position;
                    this.position = newPosition;
                    this.positionChanged(oldPosition, newPosition);
                }
            }
        }
    }
    void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }
    void removeObserver(IPositionChangeObserver observer){
        this.observers.remove(observer);
    }
    void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver observer: observers){
            observer.positionChanged(oldPosition, newPosition);
        }
    }
}
