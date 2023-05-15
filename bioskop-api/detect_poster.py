import requests
import csv

f = open("with_poster.csv", 'w', encoding='utf-8')
writer = csv.writer(f)

with open("movies_metadata.csv", encoding="utf-8-sig") as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    line = 0
    count = 0
    for row in csv_reader:
        if line == 0:
            line += 1
        else:  
            response = requests.get(row[5])
            if response.status_code == 200:
                writer.writerow(row)
                count += 1
                print(count)
            line += 1
f.close()

