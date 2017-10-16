# Spiegel
Zum starten der Middleware 
Datei: ~/.gradle/gradle.properties muss vorhanden sein und folgendes enthalten:

cstiNexusURLPrivate=https://dev.csti.haw-hamburg.de/nexus/repository/maven-private/
cstiNexusURLSnapshots=https://dev.csti.haw-hamburg.de/nexus/repository/maven-snapshots/
cstiNexusURLReleases=https://dev.csti.haw-hamburg.de/nexus/repository/maven-releases/
cstiNexusUser=vorname.nachname
cstiNexusPassword=pwd

Die Middleware lässt sich starten durch:
cd middleware-2.1.0-SNAPSHOT/
./bin/middleware
