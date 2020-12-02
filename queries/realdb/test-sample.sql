-- A basic set of queries. May need to be edited a bit.
-- RUN: db2 -stvf test-sample.sql > test-sample.out

-- Add a trainer
INSERT INTO Trainer (id, name, gender) VALUES (1, 'foo', 'M');

-- Give trainer a pokemon
INSERT INTO OwnedPokemon (trainer_id, pkmn_id, nickname, in_team, m1, ability) VALUES (1, 1, 'bar', True, 'Tackle', 'Overgrow');

-- Get the full info of the Pkmn in foo's team
SELECT trainer_id, nickname, Pokemon.name as name, m1, ability FROM OwnedPokemon JOIN Pokemon ON OwnedPokemon.pkmn_id=Pokemon.id WHERE trainer_id=1 AND in_team=True;

-- Get the moves and ability info of the pkm
SELECT name, description, power FROM Moves WHERE name IN ('Tackle');
SELECT name, boost_stat FROM Abilities WHERE name='Overgrow';

-- Cleanup
DELETE FROM Trainer WHERE name='foo';
