package baseNoStates;

// Before executing enable assertions :
// https://se-education.org/guides/tutorials/intellijUsefulSettings.html

import baseNoStates.requests.Request;
import baseNoStates.requests.RequestArea;
import baseNoStates.requests.RequestReader;
import baseNoStates.spaces.DirectoryAreas;
import baseNoStates.clock.Clock;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.MDC;

public class Main {
  private static final Logger LOG = LoggerFactory.getLogger(Main.class);
  public static void main(String[] args) {
      Clock.getInstance().start();
    DirectoryAreas.makeAreas();
    DirectoryUsers.makeUsers();
    LOG.debug("Starting webserver");
    new WebServer();
  }
}
