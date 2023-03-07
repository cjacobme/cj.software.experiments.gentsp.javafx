package cj.software.experiments.gentsp.javafx;

import cj.software.experiments.gentsp.entity.ProblemSetup;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.util.Callback;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

public class FxmlNewProblemDialog extends Dialog<ProblemSetup> {
    private NewProblemController newProblemController;

    public FxmlNewProblemDialog(ConfigurableApplicationContext applicationContext, Window owner) {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        FxControllerAndView<NewProblemController, DialogPane> controllerAndView =
                fxWeaver.load(NewProblemController.class);
        Optional<DialogPane> optDialog = controllerAndView.getView();
        if (optDialog.isPresent()) {
            this.newProblemController = controllerAndView.getController();
            DialogPane dialogPane = optDialog.get();
            initOwner(owner);
            initModality(Modality.APPLICATION_MODAL);
            setDialogPane(dialogPane);
            setResultConverter(new Callback<>() {
                @Override
                public ProblemSetup call(ButtonType buttonType) {
                    ProblemSetup result;
                    ButtonBar.ButtonData buttonData = buttonType.getButtonData();
                    if (buttonData.equals(ButtonBar.ButtonData.OK_DONE)) {
                        int numCities = newProblemController.getNumberCities();
                        int populationSize = newProblemController.getPopulationSize();
                        int numCycles = newProblemController.getNumCycles();
                        int elitismCount = newProblemController.getElitismCount();
                        double crossoverRate = newProblemController.getCrossoverRate();
                        int tournamentSize = newProblemController.getTournamentSize();
                        double mutationRate = newProblemController.getMutationRate();
                        result = new ProblemSetup(
                                numCities,
                                populationSize,
                                numCycles,
                                elitismCount,
                                crossoverRate,
                                tournamentSize,
                                mutationRate);
                    } else {
                        result = null;
                    }
                    return result;
                }
            });
        }
    }
}
