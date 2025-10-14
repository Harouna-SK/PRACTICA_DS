package baseNoStates;

// Before executing enable assertions :
// https://se-education.org/guides/tutorials/intellijUsefulSettings.html

import baseNoStates.requests.Request;
import baseNoStates.requests.RequestArea;
import baseNoStates.requests.RequestReader;
import baseNoStates.spaces.DirectoryAreas;
import baseNoStates.clock.Clock;

import java.time.LocalDateTime;

public class Main {
  public static void main(String[] args) {
      Clock.getInstance().start();
    DirectoryAreas.makeAreas();
    DirectoryUsers.makeUsers();
    new WebServer();
  }
}
