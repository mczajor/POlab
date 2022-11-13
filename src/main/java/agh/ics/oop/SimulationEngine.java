package agh.ics.oop;
import java.util.ArrayList;

public class SimulationEngine implements IEngine {
    private final AbstractWorldMap map;
    private final MoveDirection[] directions;
    private int numOfAnimals = 0;
    private ArrayList<Animal> animalsOnMap = new ArrayList<>();

    public SimulationEngine(MoveDirection[] directions, AbstractWorldMap map,  Vector2d[] positions){
        this.map = map;
        this.directions = directions;
        for (Vector2d position: positions){
            Animal animal = new Animal(map, position);
            boolean added = map.place(animal);
            animal.addObserver(map);
            if(added){
                animalsOnMap.add(animal);
                numOfAnimals++;
            }
        }
    }
    @Override
    public void run(){
        int i = 0;
        for (MoveDirection direction: directions){
            animalsOnMap.get(i%numOfAnimals).move(direction);
            i++;
        }
    }
}
