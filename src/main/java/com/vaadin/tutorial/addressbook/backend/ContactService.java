package com.vaadin.tutorial.addressbook.backend;

import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Separate Java service class.
 * Backend implementation for the address book application, with "detached entities"
 * simulating real world DAO. Typically these something that the Java EE
 * or Spring backend services provide.
 */
// Backend service class. This is just a typical Java backend implementation
// class and nothing Vaadin specific.
public class ContactService {

    // Create dummy data by randomly combining first and last names
    static String[] fnames = { "Peter", "Alice", "John", "Mike", "Olivia",
            "Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene", "Lisa",
            "Linda", "Timothy", "Daniel", "Brian", "George", "Scott",
            "Jennifer" };
    
    static String[] lnames = { "Smith", "Johnson", "Williams", "Jones",
            "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor",
            "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin",
            "Thompson", "Young", "King", "Robinson" };
    
    static String[] tasks = { "Groceries", "Gas", "Cat food", "Drop off kids"};

    private static ContactService instance;

    public static ContactService createDemoService() {
        if (instance == null) {

            final ContactService contactService = new ContactService();

            Random r = new Random(0);
            Calendar cal = Calendar.getInstance();
            for (int i = 0; i < 100; i++) {
                ToDoItem todo = new ToDoItem();
                todo.setFirstName(fnames[r.nextInt(fnames.length)]);
                todo.setLastName(lnames[r.nextInt(fnames.length)]);
                todo.setTask(tasks[r.nextInt(tasks.length)]);
                cal.set(1930 + r.nextInt(70),
                        r.nextInt(11), r.nextInt(28));
                todo.setStartDate(cal.getTime());
                
                cal.set(1930 + r.nextInt(70),
                        r.nextInt(11), r.nextInt(28));
                todo.setExpectedEndDate(cal.getTime());
                
                contactService.save(todo);
            }
            instance = contactService;
        }

        return instance;
    }

    private HashMap<Long, ToDoItem> contacts = new HashMap<>();
    private long nextId = 0;

    public synchronized List<ToDoItem> findAll(String stringFilter) {
        ArrayList arrayList = new ArrayList();
        for (ToDoItem contact : contacts.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase()
                                .contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ContactService.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<ToDoItem>() {

            @Override
            public int compare(ToDoItem o1, ToDoItem o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

    public synchronized long count() {
        return contacts.size();
    }

    public synchronized void delete(ToDoItem value) {
        contacts.remove(value.getId());
    }

    public synchronized void save(ToDoItem entry) {
        if (entry.getId() == null) {
            entry.setId(nextId++);
        }
        try {
            entry = (ToDoItem) BeanUtils.cloneBean(entry);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        contacts.put(entry.getId(), entry);
    }

}
