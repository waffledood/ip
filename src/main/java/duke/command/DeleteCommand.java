package duke.command;
import duke.DukeException;
import duke.gui.Ui;
import duke.task.Storage;
import duke.task.Task;
import duke.task.TaskList;

/**
 * DeleteCommand representst the user's action of deleting a task.
 */
public class DeleteCommand extends Command {

    private String taskIndexString;

    /**
     * Initializes the DeleteCommand with the CommandType Enum & a task index.
     * @param commandType User requested CommandType Enum
     * @param taskIndex index of task to be deleted
     */
    public DeleteCommand(CommandType commandType, String taskIndex) {
        super(commandType);
        this.taskIndexString = taskIndex;
    }

    /**
     * The logic to execute the DeleteCommand
     * @param taskList The TaskList object containing existing tasks.
     * @param ui The Ui object for interacting with the user.
     * @param storage The Storage object for saving & loading tasks.
     */
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {

        try {
            // check if the user input an int
            if (!taskIndexString.trim().matches("\\d+")) {
                throw new DukeException("I'm sorry, you missed out the task index");
            }

            int taskIndex = Integer.valueOf(taskIndexString.trim()) - 1;

            // if user-specified task index is out of the list
            if (taskIndex >= taskList.size()) {
                throw new DukeException("I'm sorry, you're referencing a task that does not exist!");
            }

            Task task = taskList.remove(taskIndex);
            assert task != null;
            ui.deleteTask(task);
        } catch (DukeException e) {
            return ui.showError(e.getMessage());
        }

        storage.updateFileContents(taskList);
        return ui.generateDukeResponse();
    }

    /**
     * A getter method to indicate if the chat session with Duke is active.
     * @return boolean indicating if the chat session is active or not.
     */
    @Override
    public boolean getActiveStatus() {
        return super.isActive;
    }

}
