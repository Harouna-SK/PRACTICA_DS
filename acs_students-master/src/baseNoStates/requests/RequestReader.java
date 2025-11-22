package baseNoStates.requests;

import baseNoStates.DirectoryDoors;
import baseNoStates.DirectoryUsers;
import baseNoStates.Door;
import baseNoStates.User;
import java.time.LocalDateTime;
import java.util.ArrayList;

import baseNoStates.spaces.DirectoryAreas;
import org.json.JSONArray;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class RequestReader implements Request {
  private final String credential; // who
  private final String action;     // what
  private final LocalDateTime now; // when
  private final String doorId;     // where
  private String userName;
  private boolean authorized;
  private final ArrayList<String> reasons; // why not authorized
  private String doorStateName;
  private boolean doorClosed;
  private static final Logger LOG = LoggerFactory.getLogger(RequestReader.class);
  public RequestReader(String credential, String action, LocalDateTime now, String doorId) {
    LOG.debug("Initializing RequestReader object with credentials {} action {} doorId {} now {}", credential, action, doorId, now);
    this.credential = credential;
    this.action = action;
    this.doorId = doorId;
    reasons = new ArrayList<>();
    this.now = now;
  }

  public String getCredential(){
    return credential;
  }

  public LocalDateTime getNow(){
    return now;
  }

  public void setDoorStateName(String name) {
    doorStateName = name;
  }

  public String getAction() {
    return action;
  }

  public boolean isAuthorized() {
    return authorized;
  }

  public void addReason(String reason) {
    reasons.add(reason);
  }


  @Override
  public String toString() {
    if (userName == null) {
      userName = "unknown";
    }
    return "Request{"
            + "credential=" + credential
            + ", userName=" + userName
            + ", action=" + action
            + ", now=" + now
            + ", doorID=" + doorId
            + ", closed=" + doorClosed
            + ", authorized=" + authorized
            + ", reasons=" + reasons
            + "}";
  }

  public JSONObject answerToJson() {
    JSONObject json = new JSONObject();
    json.put("authorized", authorized);
    json.put("action", action);
    json.put("doorId", doorId);
    json.put("closed", doorClosed);
    json.put("state", doorStateName);
    json.put("reasons", new JSONArray(reasons));
    LOG.debug("JSONObject created");
    return json;
  }

  // see if the request is authorized and put this into the request, then send it to the door.
  // if authorized, perform the action.
  public void process() {
    User user = DirectoryUsers.findUserByCredential(credential);
    Door door = DirectoryAreas.findDoorById(doorId);
    assert door != null : "door " + doorId + " not found";
    authorize(user, door);
    // this sets the boolean authorize attribute of the request
    door.processRequest(this);
    // even if not authorized we process the request, so that if desired we could log all
    // the requests made to the server as part of processing the request
    doorClosed = door.isClosed();
  }

  // the result is put into the request object plus, if not authorized, why not,
  // only for testing
  /*
  private void authorize(User user, Door door) {
    if (user == null) {
      authorized = false;
      addReason("user doesn't exists");
    }else{
      var targetSpace = door.getTo(); // espai on vol accedir
      if (targetSpace == null) {
        authorized = false;
        addReason("Door has no target space");
        return;
      }
      // Comprovar si l’usuari pot estar en aquest espai
      if (user.canBeInSpace(targetSpace)) {
        authorized = true;
      } else {
        authorized = false;
        addReason("User not authorized to access space " + targetSpace.getId());
      }
    }
  }
}

*/

private void authorize(User user, Door door) {
    if (user == null) {
        authorized = false;
        addReason("User doesn't exist");
        LOG.debug("User does not exist");
        return;
    }
    LOG.debug("Proceesing request for user {} and door {}", user.getCredential(), door.getId());

    var targetSpace = door.getTo(); // area a la que da acceso la puerta

    boolean canPerform = user.getGroup().canPerform(action, targetSpace, now);

    if (canPerform) {
        authorized = true;
        userName = user.toString();
    } else {
        authorized = false;

        // verificar que permiso no se ha cumplido
        var group = user.getGroup();

        if (!group.getAllowedActions().contains(action)) {
            addReason("Action '" + action + "' not allowed for group " + group.getName());
            LOG.debug("Action '" + action + "' not allowed for group " + group.getName());
        }
        if (!group.getAllowedSpaces().contains(targetSpace)) {
            addReason("Space '" + targetSpace.getId() + "' not allowed for group " + group.getName());
            LOG.debug("Space '" + targetSpace.getId() + "' not allowed for group " + group.getName());
        }
        if (group.getSchedule() == null) {
            addReason("Group has no schedule");
            LOG.debug("Group has no schedule");
        } else if (!group.getSchedule().isWithinSchedule(now)) {
            addReason("Current date/time not within schedule for group " + group.getName());
            LOG.debug("Current date/time not within schedule for group " + group.getName());
        }
    }

  }
}
