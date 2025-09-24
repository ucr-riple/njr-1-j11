'''
This script stores the main class name in all
project folders. This is a little non-trivial 
because of the following case:
One of the main functions is as follows ->
uy.edu.fing.mina.fsa.test.RateAndPower_1

So we can't just convert all _ to . in the 
last part of the jar file name.
Instead, we loop through all the mainclass
names in the 'info/mainclasses' file from
each project, and try to check which one
matches the jar file name.
'''
import os

BENCHMARKS_FOLDER = "big"

#Loop through the benchmarks
for benchmark in os.listdir(BENCHMARKS_FOLDER):
    benchmark_path = os.path.join(BENCHMARKS_FOLDER,benchmark)
    #skip non-directories
    if not os.path.isdir(benchmark_path):
        continue

    #Get main class according to the jar file name
    jarfile = ''
    for file in os.listdir(os.path.join(benchmark_path,"jarfile")):
        if file.endswith(".jar"):
            jarfile = file
    jarfile_path = os.path.join(benchmark_path,("jarfile/" + jarfile))
    #retreive praxis.PraxisController 
    #from url573972e02c_NotaLabs_Praxis_tgz-pJ8-praxis_PraxisControllerJ8.jar 
    jar_path_part3 = jarfile.split('-')[2]
    mainclass_from_jar = jar_path_part3[:-6] #remove J8.jar

    #Get all the main classes according to the 'info/mainclasses' file
    info_mainclasses_file = os.path.join(benchmark_path,"info/mainclasses")
    with open(info_mainclasses_file) as fp:
        mainclasses = fp.read().splitlines()
    correct_main_class_name = ""
    matches = 0
    for cl in mainclasses:
        #match with the main class from jar.
        if (cl.replace(".","_")==mainclass_from_jar):
            correct_main_class_name = cl
            matches += 1

    if matches!=1: #not found
        print(mainclass_from_jar + "," + str(matches))
    else:
        file_to_store_info = os.path.join(benchmark_path,"info/mainclassname")
        with open(file_to_store_info, 'w') as fp2:
            fp2.write(correct_main_class_name)
