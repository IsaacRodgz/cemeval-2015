Cemeval-2015

From the command line:

Proyect AnalisisGraficas Generates graphs with analysis of error between GoldStandard and Output. Generates a .pdf file, .jpg images with the graphs and a .cvs file with a confusion matrix

Compiling: javac -cp javacsv.jar:itext-2.1.7.jar:itextpdf-5.5.3.jar:jcommon-1.0.23.jar:jfreechart-1.0.19.jar Graficas.java Imagen.java Lector.java MyComparator.java

Running: java -cp .:javacsv.jar:itext-2.1.7.jar:itextpdf-5.5.3.jar:jcommon-1.0.23.jar:jfreechart-1.0.19.jar Graficas

Example: java -cp .:javacsv.jar:itext-2.1.7.jar:itextpdf-5.5.3.jar:jcommon-1.0.23.jar:jfreechart-1.0.19.jar Graficas STS.output.images.txt STS.gs.images.txt grafica.csv

Proyect OrdenacionFrases Sorts phrases as a function of error, in descending order. Generates a .csv file

Compiling: javac -cp javacsv.jar GeneradorCsv.java Imagen.java Lector.java MyComparator.java

Running: java -cp .:javacsv.jar GeneradorCsv

Example: java -cp .:javacsv.jar GeneradorCsv STS.input.images.txt STS.output.images.txt STS.gs.images.txt matriz.csv
