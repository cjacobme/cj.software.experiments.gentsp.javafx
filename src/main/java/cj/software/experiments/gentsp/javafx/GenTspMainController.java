package cj.software.experiments.gentsp.javafx;

import cj.software.experiments.gentsp.entity.*;
import cj.software.experiments.gentsp.javafx.control.WorldPane;
import cj.software.experiments.gentsp.util.PopulationFactory;
import cj.software.experiments.gentsp.util.RatingCalculator;
import cj.software.experiments.gentsp.util.WorldFactory;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;

@Component
@FxmlView("GenTspMain.fxml")
public class GenTspMainController implements Initializable {
    private static final String INT_FORMAT = "%30s = %d";

    private static final String DOUBLE_FORMAT = "%30s = %10.4f";

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private WorldFactory worldFactory;

    @Autowired
    private PopulationFactory populationFactory;

    @Autowired
    private RatingCalculator ratingCalculator;

    @FXML
    private BorderPane mainBorder;

    @FXML
    private TableView<Individual> tblIndividuals;

    @FXML
    private TableColumn<Individual, Integer> tcolCycle;

    @FXML
    private TableColumn<Individual, String> tcolDistanceSum;

    private WorldPane worldPane;

    private final Logger logger = LogManager.getFormatterLogger();

    @FXML
    public void exitApplication() {
        logger.info("exiting now...");
        Platform.exit();
    }

    @FXML
    public void newProblem() {
        Window owner = Window.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        FxmlNewProblemDialog dialog = new FxmlNewProblemDialog(applicationContext, owner);
        Optional<ProblemSetup> optionalProblemSetup = dialog.showAndWait();
        if (optionalProblemSetup.isPresent()) {
            logger.info("new problem setup was defined");
            ProblemSetup problemSetup = optionalProblemSetup.get();
            int width = problemSetup.getWidth();
            int height = problemSetup.getHeight();
            int numCities = problemSetup.getNumCities();
            int populationSize = problemSetup.getPopulationSize();
            logger.info(INT_FORMAT, "world width", width);
            logger.info(INT_FORMAT, "world height", height);
            logger.info(INT_FORMAT, "number of cities", numCities);
            logger.info(INT_FORMAT, "population size", populationSize);
            logger.info(INT_FORMAT, "number of cycles", problemSetup.getMaxGenerations());
            logger.info(INT_FORMAT, "elitism count", problemSetup.getElitismCount());
            logger.info(DOUBLE_FORMAT, "crossover rate", problemSetup.getCrossoverRate());
            logger.info(INT_FORMAT, "tournament size", problemSetup.getTournamentSize());
            logger.info(DOUBLE_FORMAT, "mutation rate", problemSetup.getMutationRate());

            World world = worldFactory.createWorld(width, height, numCities);
            logger.info("world created");
            worldPane.setWorld(world);
            List<City> cities = world.getCities();

            Population population = populationFactory.create(0, populationSize, numCities);
            logger.info("population created");
            Map<CityPair, Double> existingDistances = new HashMap<>();
            ratingCalculator.calcPopulationFitness(population, cities, existingDistances);
            List<Individual> individuals = ratingCalculator.sortFitness(population);
            Individual best = individuals.get(0);
            logger.info("best individual has dist sum  %8.2f and fitness %8.8f", best.getDistanceSum(), best.getFitnessValue());
            Individual worst = individuals.get(individuals.size() - 1);
            logger.info("worst individual has dist sum %8.2f and fitness %8.8f", worst.getDistanceSum(), worst.getFitnessValue());
            logger.info("population has fitness sum                         %8.8f", population.getPopulationFitness());
            ObservableList<Individual> tableData = FXCollections.observableList(individuals);
            tblIndividuals.setItems(tableData);
            if (!individuals.isEmpty()) {
                tblIndividuals.getSelectionModel().select(0);
            }
        } else {
            logger.info("that was cancelled");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        worldPane = new WorldPane();
        mainBorder.setCenter(worldPane);

        tcolCycle.setCellValueFactory(new PropertyValueFactory<>("cycleCounter"));
        tcolDistanceSum.setCellValueFactory(cellData -> {
            double distanceSum = cellData.getValue().getDistanceSum();
            String formatted = String.format("%7.2f", distanceSum);
            return new SimpleStringProperty(formatted);
        });
        tblIndividuals.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(
                    ObservableValue<? extends Individual> observableValue,
                    Individual oldValue,
                    Individual newValue) {
                worldPane.setCurrentIndividual(newValue);
            }
        });
    }
}
