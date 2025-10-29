/**
 * @author Thiphane Silva 69882
 * @author Rodrigo Moura 71429
 */
package campus_app.entity.student;

import campus_app.app.Position;
import campus_app.entity.service.Service;
import dataStructures.Comparator;

public class DistanceComparator implements Comparator<Service> {
    private final Position position;
    public DistanceComparator(Position position) {
        this.position = position;
    }
    @Override
    public int compare(Service x, Service y) {
        long diff = (position.getManhattanDistance(x.getPosition())) - (position.getManhattanDistance(y.getPosition()));
        if(diff < 0)return -1;
        else if(diff > 0)return 1;
        else return 0;
    }
}
