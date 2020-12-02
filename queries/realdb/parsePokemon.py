
import json

abilities={}
wirte_to = open("pokemon.txt","w+")

def parse_abilities(loa):
    for ability in loa:
        if ability not in abilities:
            abilities[ability] = len(abilities) + 1

def gen_abilities(loa):
    s="\""
    index = 0
    for ability in loa:
        if index ==0:
            s += str(abilities[ability])
        else:
            s += "," + str(abilities[ability])
        index +=1 
    s += "\""
    return s

def gen_moves(lom):
    s="\""
    i = 0
    for move in lom:
        if i == 0:
            s += move
        else:
            s += "," + move
        i += 1
    s += "\""
    return s

def gen_capture_rate():
    return 1000

with open("pokedex.json") as json_file:
    data = json.load(json_file)
    # print(json.dumps(data, indent=4))
    line_count  =0 

    for row in data:
        line_count += 1
        if line_count >=2:
            parse_abilities(row["Abilities"])

with open("ascii.json") as json_file:
    ascii_data = json.load(json_file)
    # print(json.dumps(data, indent=4))
    line_count  =0 

    
with open("pokemon.csv", 'w') as p:
    for row in data:
        row_count = int(row["Id"])
        s = ""
        s +=row["Id"] + ','
        s += row["Name"] + ','
        type1 = row["Type 1"] if row["Type 1"] != "None" else ''
        s += type1 + ','
        type2 = row["Type 2"] if row["Type 2"] != "None" else ''
        s += type2 + ','
        # s +=gen_abilities(row["Abilities"]) + ','
        category = row["Category"] if row["Category"] != "None" else ''
        s += category + ','
        # s +=row["Height (ft)"] + ','
        # s +=row["Height (m)"] + ','
        # s +=row["Weight (lbs)"] + ','
        # s +=row["Weight (kg)"] + ','
        s += row["Capture Rate"] + ','
        # s +=row["Egg Steps"] + ','
        # s +=row["Exp Group"] + ','
        # s +=row["Total"] + ','
        s +=row["HP"] + ','
        s +=row["Attack"] + ','
        # s +=row["Defense"] + ','
        # s +=row["Sp. Attack"] + ','
        # s +=row["Sp. Defense"] + ','
        # s +=row["Speed"] + ','
        # s +=gen_moves(row["Moves"]) + ','
        s += '"' +ascii_data.get(row["Id"].lstrip("0"), {}).get("ascii", '').replace('\n', '|') + '",'
        s += "\n"
        p.write(s)

    

