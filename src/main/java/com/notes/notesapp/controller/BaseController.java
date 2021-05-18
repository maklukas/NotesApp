package com.notes.notesapp.controller;

import com.notes.notesapp.domain.Note;
import com.notes.notesapp.service.NoteService;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("")
public class BaseController extends VerticalLayout {

    private NoteService noteService;

    public BaseController(NoteService noteService) {
        this.noteService = noteService;

        showAllNotes();
        showCleanNote();
    }

    private void showCleanNote() {
        Notification notificationAdd = new Notification(
                "Note added..", 3000);
        DateTimePicker dateTimePickerNote = new DateTimePicker();
        dateTimePickerNote.setDatePlaceholder("Date");
        dateTimePickerNote.setTimePlaceholder("Time");

        TextArea textAreaNote = new TextArea();
        textAreaNote.getStyle().set("minHeight", "300px").set("minWidth", "430px");
        textAreaNote.setPlaceholder("Enter your note here!");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        Icon iconAdd = new Icon(VaadinIcon.FILE_ADD);
        iconAdd.addClickListener(click -> {
            noteService.createNote(new Note(dateTimePickerNote.getValue(), textAreaNote.getValue()));
            notificationAdd.open();
            removeAll();
            showAllNotes();
            dateTimePickerNote.clear();
            textAreaNote.clear();
            add(horizontalLayout, textAreaNote);
        });

        horizontalLayout.add(dateTimePickerNote, iconAdd);
        add(horizontalLayout, textAreaNote);
    }

    private void showAllNotes() {
        Notification notificationDelete = new Notification(
                "Note deleted..", 3000);
        Notification notificationUpdate = new Notification(
                "Note updated..", 3000);

        List<Note> notes = noteService.getNotes();
        for (Note n: notes) {
            DateTimePicker dateTimePickerNote = new DateTimePicker();
            dateTimePickerNote.setValue(n.getDateTime());
            dateTimePickerNote.addValueChangeListener(change -> {
                Map<String, Object> update = new HashMap<>();
                update.put("dateTime", change.getValue());
                noteService.partialUpdate(n, update);
                notificationUpdate.open();
            });

            TextArea textAreaNote = new TextArea();
            textAreaNote.getStyle().set("minHeight", "300px").set("minWidth", "430px");
            textAreaNote.setValue(n.getNote());
            textAreaNote.addValueChangeListener(change -> {
                    Map<String, Object> update = new HashMap<>();
                    update.put("note", change.getValue());
                    noteService.partialUpdate(n, update);
                    notificationUpdate.open();
            });

            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);

            Icon iconDelete = new Icon(VaadinIcon.CLOSE_CIRCLE);
            iconDelete.addClickListener(click -> {
                noteService.deleteNote(n);
                notificationDelete.open();
                removeAll();
                showAllNotes();
                showCleanNote();
            });

            horizontalLayout.add(dateTimePickerNote, iconDelete);
            add(horizontalLayout, textAreaNote);
        }
    }
}
