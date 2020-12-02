import json

with open('pokedex.json') as json_file:
    data = json.load(json_file)
    moves_seen = set()
    output = ''
    for pokemon in data:
        for move in pokemon["Moves"]:
            if move not in moves_seen:
                line = move + ','
                for field in pokemon["Moves"][move]:
                    if field == 'Level':
                        continue
                    n = pokemon["Moves"][move][field]
                    if n == '--':
                        n = ''
                    elif n == '??':
                        n = ''
                    line += n + ','
                output += line[:len(line)-2] + '\n'
                moves_seen.add(move)
    with open('moves.csv', 'w') as outfile:
        outfile.write(output)