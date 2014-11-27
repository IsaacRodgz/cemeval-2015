import java.util.*;

class MyComparator implements Comparator<Imagen> {
@Override
public int compare(Imagen o1, Imagen o2) {
    if (o1.getDiferenciaDouble() > o2.getDiferenciaDouble()) {
        return -1;
    } else if (o1.getDiferenciaDouble() < o2.getDiferenciaDouble()) {
        return 1;
    }
    return 0;
}}
