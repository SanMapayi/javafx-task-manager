# JavaFX Task Manager

A JavaFX desktop application for managing tasks, descriptions, and due dates.

## What is implemented

FXML views, controllers, observable task lists, dialogs, and local file persistence.

## Build and run

Use JDK 23 or newer and Maven from the repository root. The Maven file is the source of truth for any lower compiler target.

~~~sh
mvn -B compile
mvn javafx:run
~~~

## Scope and limitations

Requires a graphical desktop for the UI. Local task data is excluded from Git. Persistence uses the existing text format and localised dates; cross-locale interchange and malformed-file recovery remain limitations. Saving now truncates stale records, and a missing data file starts with an empty list.

## Verification

See [verification notes](docs/verification.md) for the exact checks performed. A compile result is not an end-to-end test result.

Local configuration, credentials, browser drivers, generated reports, IDE metadata, and personal data are excluded from new source commits. Existing source notices are retained.