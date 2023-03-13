package cj.software.experiments.gentsp.javafx;

import cj.software.experiments.gentsp.entity.*;
import cj.software.experiments.gentsp.event.MultipleCyclesEvent;
import cj.software.experiments.gentsp.event.MultipleCyclesListener;
import cj.software.experiments.gentsp.javafx.control.TextFieldFormatter;
import cj.software.experiments.gentsp.javafx.control.WorldPane;
import cj.software.experiments.gentsp.util.*;
import cj.software.experiments.gentsp.util.spring.SpringContext;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

    @Autowired
    private MatingService matingService;

    @FXML
    private BorderPane mainBorder;

    @FXML
    private TableView<Individual> tblIndividuals;

    @FXML
    private TableColumn<Individual, Integer> tcolCycle;

    @FXML
    private TableColumn<Individual, String> tcolDistanceSum;

    @FXML
    private TextField tfNumberCycles;

    @FXML
    private Button btnRun;

    @FXML
    private Button btnStep;

    @FXML
    private TextField tfPopulationFitness;

    @FXML
    private TextField tfCycleCounter;

    private WorldPane worldPane;

    private ProblemSetup problemSetup;

    private Population population;

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
            problemSetup = optionalProblemSetup.get();
            int width = problemSetup.getWidth();
            int height = problemSetup.getHeight();
            int numCities = problemSetup.getNumCities();
            int populationSize = problemSetup.getPopulationSize();
            logger.info(INT_FORMAT, "world width", width);
            logger.info(INT_FORMAT, "world height", height);
            logger.info(INT_FORMAT, "number of cities", numCities);
            logger.info(INT_FORMAT, "population size", populationSize);
            logger.info(INT_FORMAT, "elitism count", problemSetup.getElitismCount());
            logger.info(DOUBLE_FORMAT, "crossover rate", problemSetup.getCrossoverRate());
            logger.info(INT_FORMAT, "tournament size", problemSetup.getTournamentSize());
            logger.info(DOUBLE_FORMAT, "mutation rate", problemSetup.getMutationRate());

            World world = worldFactory.createWorld(width, height, numCities);
            logger.info("world created");
            worldPane.setWorld(world);

            Population createdPopulation = populationFactory.create(0, populationSize, numCities);
            logger.info("population created");
            this.setPopulation(createdPopulation);
        } else {
            logger.info("that was cancelled");
        }
    }

    private void setPopulation(Population population) {
        if (this.population != null) {
            List<Individual> currentRated = ratingCalculator.sortFitness(this.population);
            double currentDistSum = currentRated.get(0).getDistanceSum();
            List<Individual> newRated = ratingCalculator.sortFitness(population);
            double newDistSum = newRated.get(0).getDistanceSum();
            if (newDistSum > currentDistSum) {
                logger.error("Oops");
            }
        }
        List<City> cities = worldPane.getWorld().getCities();
        this.population = population;
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
        double populationFitness = population.getPopulationFitness();
        String formatted = String.format("%7.6f", populationFitness);
        tfPopulationFitness.setText(formatted);
        formatted = String.format("%d", problemSetup.getCycleCounter());
        tfCycleCounter.setText(formatted);
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
        TextFieldFormatter textFieldFormatter = SpringContext.getBean(TextFieldFormatter.class);
        textFieldFormatter.initInt(tfNumberCycles, 10000);
        tfNumberCycles.editableProperty().bind(worldPane.worldProperty().isNotNull());
        btnRun.disableProperty().bind(worldPane.worldProperty().isNull());
        btnStep.disableProperty().bind(worldPane.worldProperty().isNull());
    }

    @FXML
    public void step() {
        logger.info("perform 1 step...");
        int cycleCounter = problemSetup.incCycleCounter();
        double crossoverRate = problemSetup.getCrossoverRate();
        int elitismCount = problemSetup.getElitismCount();
        int tournamentSize = problemSetup.getTournamentSize();
        double mutationRate = problemSetup.getMutationRate();
        Population newPopulation = matingService.crossOver(
                this.population,
                crossoverRate,
                elitismCount,
                tournamentSize,
                cycleCounter,
                mutationRate);
        this.setPopulation(newPopulation);
    }

    @FXML
    public void runCycles() {
        MyMultipleCyclesListener listener = new MyMultipleCyclesListener();
        int numCycles = Integer.parseInt(tfNumberCycles.getText());
        List<City> cities = worldPane.getWorld().getCities();
        MultipleCyclesRunner runner = new MultipleCyclesRunner(
                matingService,
                ratingCalculator,
                problemSetup,
                population,
                cities,
                numCycles);
        runner.addMultipleCyclesListener(listener);
        Thread thread = new Thread(runner, "breed");
        thread.start();
    }

    private class MyMultipleCyclesListener implements MultipleCyclesListener {

        @Override
        public void nextCycleFinished(final MultipleCyclesEvent event) {
            GenTspMainController.this.population = event.getPopulation();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    List<Individual> individuals = event.getIndividualsSorted();
                    ObservableList<Individual> tableData = FXCollections.observableList(individuals);
                    tblIndividuals.setItems(tableData);
                    if (!individuals.isEmpty()) {
                        tblIndividuals.getSelectionModel().select(0);
                    }
                    String formatted = String.format("%7.6f", event.getPopulationFitness());
                    tfPopulationFitness.setText(formatted);
                    formatted = String.format("%d", event.getCycleCounter());
                    tfCycleCounter.setText(formatted);
                }
            });
        }

        @Override
        public void allCyclesFinished() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "All cycles calculated");
                            alert.show();
                        }
                    });
                }
            });
        }
    }
}
