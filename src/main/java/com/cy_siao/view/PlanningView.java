package com.cy_siao.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.cy_siao.dao.StayDao;
import com.cy_siao.model.Stay;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * View class for displaying the planning schedule UI components.
 * Manages a table view showing stays over a 14-day period.
 */
public class PlanningView {
    private TableView<StayRow> table; // Table displaying stay rows
    private final int NUM_DAYS = 14; // Number of days to display in the schedule
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM"); // Date formatter for column headers
    private VBox layout; // Layout container for the view

    /**
     * Constructs the PlanningView, setting up the table and loading stay data.
     *
     * @param viewManager The ViewManager to handle view transitions
     */
    public PlanningView(ViewManager viewManager) {
        table = new TableView<>();

        TableColumn<StayRow, String> bedCol = new TableColumn<>("Bed");
        bedCol.setCellValueFactory(data -> data.getValue().bedProperty());
        table.getColumns().add(bedCol);

        LocalDate today = LocalDate.now();
        for (int i = 0; i < NUM_DAYS; i++) {
            LocalDate date = today.plusDays(i);
            String header = formatter.format(date);
            TableColumn<StayRow, String> col = new TableColumn<>(header);
            final int dayOffset = i;
            col.setCellValueFactory(cellData -> cellData.getValue().getDayProperty(dayOffset));
            col.setCellFactory(tc -> new TableCell<>() {
                private final Text text = new Text();
                {
                    setGraphic(new StackPane(text));
                    setPadding(new Insets(5));
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        text.setText("");
                        setStyle("");
                        setTooltip(null);
                    } else {
                        text.setText(item);
                        Tooltip tooltip = new Tooltip(item);
                        setTooltip(tooltip);

                        StayRow row = getTableRow().getItem();
                        if (row != null && row.isDateInRange(dayOffset)) {
                            if (row.isInconsistent()) {
                                setStyle("-fx-background-color: red;");
                            } else {
                                setStyle("-fx-background-color: lightgreen;");
                            }
                        } else {
                            setStyle("-fx-background-color: white;");
                        }
                    }
                }
            });
            table.getColumns().add(col);
        }

        StayDao dao = new StayDao();
        List<Stay> stays = dao.findAll();
        stays.forEach(s -> table.getItems().add(new StayRow(s, NUM_DAYS)));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> viewManager.showMainMenu());

        layout = new VBox(10, table, backButton);
        layout.setPadding(new Insets(20));
    }

    /**
     * Gets the root node of the planning view.
     *
     * @return The root VBox layout
     */
    public Parent getView() {
        return layout;
    }
}
