# A Modern Web-based Editor

This is application is purely a multi-bundle desktop demo app using Equo Framework and Vue.js.

This project shows off how you can create a modern multi-bundle desktop application with Equo Framework. It integrates the web-based Equo Editor widget in a desktop app. The other components (toolbar, treeview, etc.) were developed using Vue.js. Please go through the steps in [CONTRIBUTING](https://github.com/equoplatform/framework/blob/main/docs/CONTRIBUTING.md) before submitting an issue.

## Build

1. Download the Eclipse Installer from [here](https://www.eclipse.org/downloads/packages/installer).
2. Open the installer and choose the option *Eclipse IDE for Java Developers*.
3. Install Eclipse.
4. Inside Eclipse, go to *Help->Install New Software*, and paste the following repo URL: `https://bndtools.jfrog.io/bndtools/update-latest`. Proceed with the installation of Bndtools and restart Eclipse if it asks for it.
5. Clone this project.
6. Import `cnf`, `com.make.equo.poc`, and `com.make.equo.widgets.shell` projects as `Existing Projects into Workspace`.
7. Go to a terminal, and in the root folder of this project run: `./gradlew release`.

## Run the Application

Once you have built the demo app, you can run it by opening the file `com.make.equo.poc/bnd.bnd` and and running it.

## Troubleshooting

1. If you find some errors in Eclipse, try cleaning the workspace by going to `Project -> Clean...`.
2. If you need help, do not hesitate to join our [Discord server](https://discord.gg/fFEEhm8etB).
