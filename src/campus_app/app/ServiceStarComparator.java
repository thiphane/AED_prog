package campus_app.app;

import campus_app.entity.service.Service;
import dataStructures.Comparator;

public class ServiceStarComparator implements Comparator<Service> {
    @Override
    public int compare(Service o1, Service o2) {
        // TODO verificar se é assim ou o2 - o1
        float diff = o2.getRealRating() - o1.getRealRating();
        // TODO deve haver maneira melhor de fazer isto, cuidado com casas decimais
        if(diff < 0) {
            return (int)Math.floor(diff);
        } else {
            // TODO verificar que se forem iguais dá 0
            return (int)Math.ceil(diff);
        }
    }
}
