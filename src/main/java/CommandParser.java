import java.lang.Exception;

public class CommandParser {
    public static String parse(String input, TaskList tasks) {
        String message = MsgConstants.MSG_EMPTY.getString(); // Dummy initialization.
        try {
            if (input.startsWith(MsgConstants.CMD_BYE.getString())) {
                // "bye" command.
                try {
                    if (input.equals(MsgConstants.CMD_BYE.getString())) {
                        tasks.hasClose();
                        message = MsgConstants.MSG_LINE.getString()
                                + MsgConstants.MSG_FAREWELL.getString()
                                + MsgConstants.MSG_LINE.getString();
                    } else {
                        throw new InvalidInputException();
                    }
                } catch (InvalidInputException e) {
                    e.unknownCmd();
                }
            } else if (input.startsWith(MsgConstants.CMD_LIST.getString())) {
                // "list" command.
                try {
                    if (input.equals(MsgConstants.CMD_LIST.getString())) {
                        // Either empty or contains something.
                        message = tasks.list();
                    } else {
                        throw new InvalidInputException();
                    }
                } catch (InvalidInputException e) {
                    e.unknownList();
                }
            } else if (input.startsWith(MsgConstants.CMD_MARK.getString())
                    || input.startsWith(MsgConstants.CMD_UNMARK.getString())) {
                // "mark" or "unmark" command.
                // Expects only one positive integer.
                String cmd = "";
                int cmdIdx = 0;
                int cmdFixIdx = ValConstants.TASK_FIX_IDX.getVal();
                if (input.startsWith(MsgConstants.CMD_MARK.getString())) {
                    cmd = MsgConstants.CMD_MARK.getString();
                    cmdIdx = ValConstants.TASK_MARK_IDX.getVal();
                } else {
                    cmd = MsgConstants.CMD_UNMARK.getString();
                    cmdIdx = ValConstants.TASK_UNMARK_IDX.getVal();
                }

                try {
                    if (input.matches(MsgConstants.TASK_MARK.getString())
                            || input.matches(MsgConstants.TASK_UNMARK.getString())) {
                        // Recall to convert to 1 based indexing
                        // NumberFormatException may be thrown here during int parsing
                        int index = Integer.parseInt(
                                input.substring(cmdIdx))
                                - cmdFixIdx;

                        // Ensure integer is valid
                        if ((index >= 0) && (index < tasks.size())) {
                            if (cmd.equals(MsgConstants.CMD_MARK.getString())) {
                                message = tasks.mark(index);
                            } else {
                                message = tasks.unmark(index);
                            }
                        } else {
                            throw new InvalidInputException();
                        }
                    } else {
                        throw new InvalidInputException();
                    }
                } catch (NumberFormatException e) {
                    (new InvalidInputException()).unknownMark(cmd, tasks.size());
                } catch (InvalidInputException e) {
                    e.unknownMark(cmd, tasks.size());
                }
            } else if (input.startsWith(MsgConstants.CMD_DELETE.getString())) {
                try {
                    if (input.matches(MsgConstants.TASK_DELETE.getString())) {
                        // Recall to convert to 1 based indexing
                        // NumberFormatException may be thrown here during int parsing
                        int index = Integer.parseInt(
                                input.substring(ValConstants.TASK_DELETE_IDX.getVal()))
                                - ValConstants.TASK_FIX_IDX.getVal();

                        // Ensure integer is valid
                        if ((index >= 0) && (index < tasks.size())) {
                            message = tasks.remove(index);
                        } else {
                            throw new InvalidInputException();
                        }
                    } else {
                        throw new InvalidInputException();
                    }
                } catch (NumberFormatException e) {
                    (new InvalidInputException())
                            .unknownMark(MsgConstants.CMD_DELETE.getString(), tasks.size());
                } catch (InvalidInputException e) {
                    e.unknownMark(MsgConstants.CMD_DELETE.getString(), tasks.size());
                }
            } else if (input.startsWith(MsgConstants.CMD_TODO.getString())) {
                // "todo" command.
                // Expects any description
                try {
                    if (input.matches(MsgConstants.TASK_TODO.getString())) {
                        Task newTask = new ToDo(input.substring(ValConstants.TASK_TODO_IDX.getVal()));
                        message = tasks.add(newTask);
                    } else {
                        throw new InvalidInputException();
                    }
                } catch (InvalidInputException e) {
                    e.unknownTodo();
                }
            } else if (input.startsWith(MsgConstants.CMD_DEADLINE.getString())) {
                try {
                    if (input.matches(MsgConstants.TASK_DEADLINE.getString())) {
                        // Deadline has a /by specification, greedily take the first in sequence.
                        String withoutDeadline = input.substring(ValConstants.TASK_DEADLINE_IDX.getVal());
                        int by_idx = withoutDeadline.indexOf(MsgConstants.CMD_BY.getString());
                        String deadlineName = withoutDeadline.substring(0, by_idx);
                        String by = withoutDeadline.substring(by_idx + ValConstants.TASK_BY_IDX.getVal());
                        Task newTask = new Deadlines(deadlineName, by);

                        message = tasks.add(newTask);
                    } else {
                        throw new InvalidInputException();
                    }
                } catch (InvalidInputException e) {
                    e.unknownDeadline();
                }
            } else if (input.startsWith(MsgConstants.CMD_EVENT.getString())) {
                try {
                    if (input.matches(MsgConstants.TASK_EVENT.getString())) {
                        // Event has /from and /to specification.
                        // Greedily take the first in sequential order.
                        String withoutEvent = input.substring(ValConstants.TASK_EVENT_IDX.getVal());
                        int from_idx = withoutEvent.indexOf(MsgConstants.CMD_FROM.getString());
                        String eventName = withoutEvent.substring(0, from_idx);
                        String remainingSplit = withoutEvent.substring(from_idx
                                + ValConstants.TASK_FROM_IDX.getVal());
                        int to_idx = remainingSplit.indexOf(MsgConstants.CMD_TO.getString());
                        String eventFrom = remainingSplit.substring(0, to_idx);
                        String eventTo = remainingSplit.substring(to_idx
                                + ValConstants.TASK_TO_IDX.getVal());

                        Task newTask = new Events(eventName, eventFrom, eventTo);
                        message = tasks.add(newTask);
                    } else {
                        throw new InvalidInputException();
                    }
                } catch (InvalidInputException e) {
                    e.unknownEvent();
                }
            } else {
                throw new InvalidInputException();
            }
        } catch (InvalidInputException e) {
            e.unknownCmd();
        } finally {
            return message;
        }
    }
}
