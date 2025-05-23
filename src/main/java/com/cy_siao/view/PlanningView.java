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

public class PlanningView {
    private TableView<StayRow> table;
    private final int NUM_DAYS = 14;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
    private VBox layout;

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

    public Parent getView() {
        return layout;
    }
}
