package baseNoStates;

// Before executing enable assertions :
// https://se-education.org/guides/tutorials/intellijUsefulSettings.html

import baseNoStates.requests.Request;
import baseNoStates.requests.RequestArea;
import baseNoStates.requests.RequestReader;
import baseNoStates.spaces.DirectoryAreas;

import java.time.LocalDateTime;

public class Main {
  public static void main(String[] args) {
    DirectoryAreas.makeAreas();
    DirectoryUsers.makeUsers();
    new WebServer();
  }
}
