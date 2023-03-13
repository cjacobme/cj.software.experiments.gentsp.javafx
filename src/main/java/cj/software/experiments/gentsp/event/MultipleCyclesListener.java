package cj.software.experiments.gentsp.event;

public interface MultipleCyclesListener {
    void nextCycleFinished(MultipleCyclesEvent event);
    void allCyclesFinished();
}
