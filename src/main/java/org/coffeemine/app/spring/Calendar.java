package org.coffeemine.app.spring;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.vaadin.stefan.fullcalendar.CalendarViewImpl;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Route
@CssImport("./styles/material-full-calendar.css")
class Calendar extends View{
    private FullCalendar systemCalendar;
    private Text date;
    HorizontalLayout calendarController;
    VerticalLayout calendar;

    Calendar(){
        super();
        this.calendarController = new HorizontalLayout();
        this.calendar = new VerticalLayout();

        Button today = new Button("Today", event -> systemCalendar.today());
        Button previous = new Button("<< Previous", event -> systemCalendar.previous());
        Button next = new Button("next >>",event -> systemCalendar.next());

        DatePicker datePicker = new DatePicker();
        datePicker.setPlaceholder("Date within this month");
        datePicker.setClearButtonVisible(true);
        datePicker.addValueChangeListener(event -> systemCalendar.gotoDate(event.getValue()));

        Select<String> selectView = new Select<>("Month", "Week", "Day");
        selectView.setPlaceholder("View as..");
        selectView.addValueChangeListener(event -> this.changeCalendarView(event.getValue()));

        Button newSprint = new Button("New sprint", event -> EventCreation.TaskCreation());
        newSprint.getStyle().set("right","auto");

        Button newTask = new Button("New task", event -> this.createNewTask());
        newTask.getStyle().set("margin-left","auto");

        calendarController.add(previous);
        calendarController.add(today);
        calendarController.add(next);
        calendarController.add(datePicker);
        calendarController.add(selectView);
        calendarController.add(newSprint);
        calendarController.add(newTask);

        systemCalendar = new FullCalendar();
        date = new Text(LocalDateTime.now().getYear() + "  "
                + LocalDateTime.now().getMonth().toString() + "  " +
                LocalDateTime.now().getDayOfMonth());
        systemCalendar.setFirstDay(DayOfWeek.MONDAY);
        systemCalendar.setBusinessHours();

        calendar.add(calendarController);
        calendar.add(date);
        calendar.add(systemCalendar);

        add(calendar);
    }

    void changeCalendarView(String viewName){
        switch(viewName) {
            case "Day":
                systemCalendar.changeView(CalendarViewImpl.DAY_GRID_DAY);
                Notification.show(viewName + " view now");
                break;
            case "Week":
                systemCalendar.changeView(CalendarViewImpl.DAY_GRID_WEEK);
                Notification.show(viewName + " view now");
                break;
            default:
                systemCalendar.changeView(CalendarViewImpl.DAY_GRID_MONTH);
                Notification.show(viewName + " view now");
                break;
        }
    }

    void createNewTask(){
        Entry newEvent = new Entry(
                "0101",
                "Nice Calendar View",
                LocalDateTime.now(),
                LocalDateTime.of(2019,11,28,12,00),
                true,
                true,
                " Deep blue",
                "This is a demo task");
        systemCalendar.addEntries(newEvent);
        Notification.show("Task " + "<Nice Calendar View>" + " is now added");
    }
}
