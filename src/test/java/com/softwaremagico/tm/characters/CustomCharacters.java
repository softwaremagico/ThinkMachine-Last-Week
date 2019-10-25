package com.softwaremagico.tm.characters;

/*-
 * #%L
 * Think Machine The Last Week
 * %%
 * Copyright (C) 2019 Softwaremagico
 * %%
 * This work is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> Valencia (Spain).
 *  
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 *  
 * If you want to read more about this license, please see <https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode>.
 * #L%
 */

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.itextpdf.text.DocumentException;
import com.softwaremagico.tm.InvalidXmlElementException;
import com.softwaremagico.tm.character.CharacterPlayer;
import com.softwaremagico.tm.character.Gender;
import com.softwaremagico.tm.character.Name;
import com.softwaremagico.tm.character.RandomizeCharacter;
import com.softwaremagico.tm.character.Surname;
import com.softwaremagico.tm.character.benefices.AvailableBeneficeFactory;
import com.softwaremagico.tm.character.benefices.BeneficeAlreadyAddedException;
import com.softwaremagico.tm.character.blessings.BlessingAlreadyAddedException;
import com.softwaremagico.tm.character.blessings.BlessingFactory;
import com.softwaremagico.tm.character.blessings.TooManyBlessingsException;
import com.softwaremagico.tm.character.characteristics.CharacteristicName;
import com.softwaremagico.tm.character.creation.CostCalculator;
import com.softwaremagico.tm.character.creation.FreeStyleCharacterCreation;
import com.softwaremagico.tm.character.factions.FactionsFactory;
import com.softwaremagico.tm.character.planets.PlanetFactory;
import com.softwaremagico.tm.character.races.RaceFactory;
import com.softwaremagico.tm.character.skills.AvailableSkillsFactory;
import com.softwaremagico.tm.configurator.MachinePdfConfigurationReader;
import com.softwaremagico.tm.file.PathManager;
import com.softwaremagico.tm.party.Party;
import com.softwaremagico.tm.pdf.complete.PartySheet;
import com.softwaremagico.tm.pdf.small.SmallPartySheet;
import com.softwaremagico.tm.random.exceptions.DuplicatedPreferenceException;
import com.softwaremagico.tm.random.exceptions.InvalidRandomElementSelectedException;
import com.softwaremagico.tm.random.selectors.CombatPreferences;

@Test(groups = "customCharacters")
public class CustomCharacters {
	private static final String LANGUAGE = "es";
	private static final String MODULE_FOLDER = "The Last Week";

	private Party party;

	private void setDescription(CharacterPlayer characterPlayer, String description, String background,
			String specialRules, String secondaryObjectives) {
		characterPlayer.getInfo().setCharacterDescription(description);
		characterPlayer.getInfo().setBackgroundDecription(background + "\n\nReglas Especiales: " + specialRules
				+ "\n\nObjetivo Secundario: " + secondaryObjectives);
	}

	@BeforeClass
	public void initialize() {
		party = new Party(LANGUAGE, PathManager.DEFAULT_MODULE_FOLDER);
		party.setPartyName("LastWeek");
		MachinePdfConfigurationReader.getInstance().setSmallPdfShieldEnabled(false);
		MachinePdfConfigurationReader.getInstance().setSmallPdfBlessingNameEnabled(true);
	}

	@Test
	public void geek() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().setGender(Gender.MALE);
		characterPlayer.getInfo().setAge(35);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("asian", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(8);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(8);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(4);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(4);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("computing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 8);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lore", "computingLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 8);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("electronics",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("landcarft",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("bureaucracy",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("search",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);

		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("hacker", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("disrespecful",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("clumsy", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros2000]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("highPerformanceComputer",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		System.out.println("Geek " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));
		
		Assert.assertEquals(CostCalculator.getCost(characterPlayer),
				FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.PEACEFUL);
		randomizeCharacter.createCharacter();

		String description = characterPlayer.getCompleteNameRepresentation()
				+ " viste con pantalones cortos, una camiseta de algún grupo de rock olvidado y una gorra del viejo y nostálgico Space Invaders. Se dedica profesionalmente a la seguridad informática y aunque no lo dice, por la seguridad con la que habla hace evidente que es bueno en ello. Ha trabajado para multiples empresas muy conocidas del sector, pero actualmente se ha hecho autónomo y tiene una empresa propia localizada en un sótano de la ciudad que llama «El Búnker». ";
		String background = "Durante la última semana, te has desplazado a tu empresa personal para intentar acceder al exterior a pesar del corte de comunicaciones que ha sufrido la ciudad. Después de cierto esfuerzo y de utilizar una radio militar como repetidor de datos, has logrado conectar brevemente con el mundo exterior. Para tu sorpresa, parece que el mundo exterior ignora lo que está sucediendo en la ciudad. Los noticiarios y periódicos no dedican ni una sola palabra a lo ocurrido y solamente en un canal oscuro de rusia has encontrado especulaciones al respecto. En la última visita a El Bunker, casi te has cruzado con un peloton de militares. No te vieron por los pelos, ya que estaban ocupados persiguiendo a otro pobre desgraciado que deambulaba por las calles. El ruido de disparos de metralleta te obligó a ocultarte en el bunker, y no te atreviste a salir hasta dos horas después. El cuerpo sin vida de aquel desgraciado te convenció para no romper el toque de queda nunca más. ";
		String specialRules = "Dispones de un ordenador portátil modificado que lo hace muy especial. Una gran variedad de software, puertos y cables te permiten conectarte a cualquier sistema informático al que tengas acceso físico, por lo que gracias a tus habilidades en la materia, puedes comprometer casi cualquier sistema con el tiempo necesario. No tienes penalización alguna a las tiradas de hacking siempre que puedas conectar directamente tu equipo al equipo que quieras atacar. ";
		String secondaryObjectives = "Como chico antisistema, quieres obtener pruebas de conspiraciones entre grandes empresas y el gobierno. Cualquier cosa que desestabilice el gobierno actual y haga caer a una o dos empresas multimillinarias te haría ser la persona más feliz de la tierra. Si además la empresa está vinculada al desastre actual... ¡no puedes dejar de pasar esta oportunidad!";

		setDescription(characterPlayer, description, background, specialRules, secondaryObjectives);

		party.addMember(characterPlayer);
	}

	@Test
	public void bill() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().setGender(Gender.MALE);
		characterPlayer.getInfo().setAge(68);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("englishSpeaking",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.getInfo().addName(new Name("Bill", characterPlayer.getLanguage(),
				characterPlayer.getModuleName(), Gender.MALE, characterPlayer.getFaction()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(6);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("aircraft",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("leadership",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lore", "weaponsLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("warfare",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("mechanics",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("slugGuns",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("artillery",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("gunnery",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("melee",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("survival",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("selfControl",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("tracking",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);

		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("disciplined",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("bluster", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("missingLeg",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("incurableDisease",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros0]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("typicalMed.Autofeed",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		System.out.println("Bill " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));
		
		Assert.assertEquals(CostCalculator.getCost(characterPlayer),
				FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.BELLIGERENT);
		randomizeCharacter.createCharacter();

		String description = characterPlayer.getCompleteNameRepresentation()
				+ " es un piloto de guerra ya retirado. En sus buenos tiempos, fue capitán de un gran bombardero de cuatro motores capaz de llevar cientos de toneladas de destrucción. Aunque le gusta jactarse de su formación militar, nunca ha vivido tiempos de guerra y por tanto, su experiencia se basa en maniobras de combates y simuladores virtuales. Después de muchas horas de preparación, su carrerá se vio truncada debido a un un accidente en unas maniobras. En dicho accidente perdió una pierna, lo que le obligó a retirarse. A pesar de su edad, tiene una forma física envidiable, únicamente mermada por su afición a los puros. ";
		String background = "Tu vieja radio militar no te ha dado noticias de lo que está ocurriendo. Saliste a la calle varias veces para presentarte voluntario para ayudar al ejército, pero solo conseguiste empujones y malos modos que te devolvieron a tu casa. Hace dos días, oistes disparos cerca de tu casa. Cogiste tu arma y te asomaste. Una ráfaga de metralleta te obligó apensártelo dos veces. Solo gracias a recitar en voz alta un viejo código númerico militar hizo que los militares no incendiaran tu casa con un lanzallamas. Aún estás esperando una explicación al respecto...";
		String specialRules = "Dispones de un código de emergencia que puedes utilizar con los militares. Si comunicas este código a un alto rango, te permite demostrar que has sido militar anteriormente, lo que te permite ganar cierta confianza con ellos y quizá sacarte de algún apuro llegado el caso. ";
		String secondaryObjectives = "Dado tu addicción al tabajo, tienes un tumor en los pulmones que resulta fatal. La falta de dinero por tu incapacidad de trabajar ha impedido que costearas cualquier tipo de tratamiento. Los últimos chequeos médicos te indican que te quedan pocas semanas de vida. Quien sabe, igual encuentras alguna forma de alargar tu vida aprovechando el caos y confusión que reina ahora. ";

		setDescription(characterPlayer, description, background, specialRules, secondaryObjectives);

		party.addMember(characterPlayer);
	}

	@Test
	public void piousGirl() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().setGender(Gender.FEMALE);
		characterPlayer.getInfo().setAge(31);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("mediterranean",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(4);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(3);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(8);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(8);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("selfControl",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lore", "religion",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 8);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lore", "demons",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("landcarft",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("bureaucracy",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("medicine",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 2);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("leadership",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("etiquette",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 2);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("empathy",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("search",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);

		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("disciplined",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("pious", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("brainwashed",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("poorLiar", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("painSensitive",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros0]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		System.out.println("Pious " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));
		
		Assert.assertEquals(CostCalculator.getCost(characterPlayer),
				FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.PEACEFUL);
		randomizeCharacter.createCharacter();

		String description = characterPlayer.getCompleteNameRepresentation()
				+ " es una persona devota y de las que coloca la religión como el centro de todo lo que hace. La religión le permite prácticamente justificar todos lo que ocurre a su alrededor, por lo que no necesita nada más. Tiene una vida serena y tranquila debido a ello. Ha visto -o cree haber visto- milagros delante suyo, lo que le demuestra que El Hacedor está siempre con ella y que todo tiene un plan divino. ";
		String background = "A pesar del toque de queda, te has dedicado a repartir consuelo por el barrio. Has visitado vecinos asustados a los que has invitado a rezar para recobrar el coraje y la fe. Durante la última noche te cruzaste con una persona poseída por el mismísimo demonio. Fuiste atacada y solo gracias a tu dios fuiste rescatada por un militar que patrullaba la zona. El militar te detuvo y te iba a encarcelar por violar el toque de queda, pero un grupo de atacantes os sorprendió desde la oscuridad de la noche y solo pudiste correr sin mirar atrás. ";
		String specialRules = "En situaciones sobrenaturales y siempre que hagas una interpretación adecuada, puedes intercambiar el valor de Voluntad con el de Fe. Por ejemplo, puedes recitar pasajes del libro sagrado para reconfortarte y pasar una tirada de miedo, o repetir en voz alta que el creador te guía y superar una tirada para mantener los nervios. ";
		String secondaryObjectives = "Te gustaría ser algo más que parte del rebaño. Una gran acción contra un gran ente del mal te podría dar el empujón para ser la primera mujer con altos cargos en tu religión. ";

		setDescription(characterPlayer, description, background, specialRules, secondaryObjectives);

		party.addMember(characterPlayer);
	}

	@Test
	public void sportGirl() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().setGender(Gender.FEMALE);
		characterPlayer.getInfo().setAge(32);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("russian", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(8);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(8);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(4);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(8);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(4);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 8);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 8);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("computing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 2);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("athletics",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("landcarft",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("investigation",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("search",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("demolitions",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 2);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("melee",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 2);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("selfControl",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);

		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("tall", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros0]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		System.out.println("SportGirl " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));
		
		Assert.assertEquals(CostCalculator.getCost(characterPlayer),
				FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.PEACEFUL);
		randomizeCharacter.createCharacter();

		String description = characterPlayer.getCompleteNameRepresentation()
				+ " es una boxeadora profesional de la vieja Rusia. Ganadora de una medalla de plata en las olimpiadas de Budapest 2036. Tiene una altura y corpulencia anormal para su género, lo que le convierte una boxeadora temible. Por desgracia, no tiene la misma soltura fuera del ring que dentro de este, lo que hace pensar que eres algo patosa. ";
		String background = "Tus contactos con la madre patria -una entrañable pareja de ancianitos- te han sugerido que esperases durante estos días. Hoy te tenías que reunir con ellos para obtener un pendrive con los colores de la bandera de Rusia (blanco, rojo y azul), en cuyo interior contenía encriptado información importante de aquello que estaba pasando. Si bien, al entrar en su casa has encontrado una carnicería. Un psicópata loco se te ha avalanzado encima y apenas has tenido tiempo de propinarle un gancho a la mandibula. Asombrosamente, solo se ha quedado un poco aturdido para saltar sobre ti minutos después. Lograste empujarlo de una patada y salir corriendo del edificio para encontrarte con un autobús en el que iban colocando a los vecinos. No has tenido más opción que entrar en el autobús y abandonar el pendrive a su suerte. Quien sabe, tienes otro idéntico en el que igual puedes guardar alguna información útil en el futuro. ";
		String specialRules = "Amas a Rusia por encima de todo. Todo aquellos que hagas directamente por Rusia te da un bonificador de +2 a Voluntad. ";
		String secondaryObjectives = "La pérdida de los datos es una falta grave para un agente como tu. Debes de encontrar cualquier información que compense esta pérdida de datos y enviarla como sea a la madre patria a una dirección de correo electrónico segura que conoces. Dispones de un pendrive con los colores de la bandera de rusia completamente vacio. Encriptará cualquier dato que se almacene en este, pero cuidado, no se pueden borrar datos una vez introducidos por lo que escoge bien lo que quieres guardar. ";

		setDescription(characterPlayer, description, background, specialRules, secondaryObjectives);

		party.addMember(characterPlayer);
	}

	@Test
	public void bandGirl() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().setGender(Gender.FEMALE);
		characterPlayer.getInfo().setAge(39);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("mediterranean",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(8);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(4);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(5);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("chemistry",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lore", "drugs",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lore", "poisons",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("landcarft",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 8);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("mechanics",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 8);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("search",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);

		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("crackPilot",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros50]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("typicalLightRevolver",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("addiction_3",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		System.out.println("BandGirl " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));
		
		Assert.assertEquals(CostCalculator.getCost(characterPlayer),
				FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.PEACEFUL);
		randomizeCharacter.createCharacter();

		String description = characterPlayer.getCompleteNameRepresentation()
				+ " es una amante de los coches, los motores y su velocidad. Se mueve en un mundo de carreras, apuestas y diversión. Cuando no está corriendo, pasa el tiempo ayudando a su familia en un taller mecánico, profesión que se le da bien y que le ha proporcionado una interesante cantidad de dinero. ";
		String background = "Durante la última semana te has ido reuniendo con tu «familia», que es como llamas a la banda organizada que regenta el viejo taller mecánico de un barrio periférico. Cada noche te has ido reuniendo con ellos para ver, como cada vez menos de la banda aparecían. Nadie conoce el motivo del toque de queda, pero desde luego, ha sido muy malo para los negocios. No has tenido ningún encargo de aquellos que te dan mucho dinero, es decir, transportar cocaína con tu coche por todos los barrios de la ciudad. No te has atrevido a mover tu deportivo del garaje, pero durante los viajes hasta el taller, has visto manchas de sangre por las aceras. ";
		String specialRules = "Tienes una adicción fuerte a la cocaína. Necesitas tomar una dosis diaria antes de finalizar el día o el día siguiente tendrás un -2 a todas las tiradas. Tomar una dosis necesita 30 minutos de tranquilidad. Actualmente llevas encima un total de 10 dosis. ";
		String secondaryObjectives = "Encontrar una nueva sustancia para sustituir tu addicción y que sea menos destructiva sería un logro. Pero si además es fácilmente vendible en el mercado negro te haría rica. ";

		setDescription(characterPlayer, description, background, specialRules, secondaryObjectives);

		party.addMember(characterPlayer);
	}

	@Test
	public void veterinary() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().setGender(Gender.FEMALE);
		characterPlayer.getInfo().setAge(31);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("arabian", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(6);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("computing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("bureaucracy",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 2);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("landcarft",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("ride",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lore", "beastsLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 8);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("medicine",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("biology",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("socialScience",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 2);

		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("tall", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("clumsy", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros0]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		System.out.println("Vet " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		Assert.assertEquals(CostCalculator.getCost(characterPlayer),
				FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.PEACEFUL);
		randomizeCharacter.createCharacter();

		String description = "A " + characterPlayer.getCompleteNameRepresentation()
				+ " le gustó desde siempre la medicina, si bien finalmente optó con ser veterinaria. Como amante de los animales vive con varios de ellos en su casa. Tiene una colección de gatos, lagartos y aves exóticas que convierten su casa en un auténtico circo. Se te da bien tu profesión, y casi prefieres la vida de un animal sobre la de una persona. ";
		String background = "Como científica que eres has intentado buscar respuestas a lo que ha ocurrido en la ciudad. Lo que te ha llevado a casi ser descubierta por un gran número de militares que se atrincheraban en la barriada. Has escapado a duras penas arrastrándote por los suelos y has acabado en una calle llena de sangre reseca. Te has llevado muestras para analizar y has descubierto que la sangre es siempre humana, y desde entonces, has caído en la cuenta de que ya no quedan animales en la ciudad. Hasta las ratas os han abandonado a vuestra suerte. ";
		String specialRules = "Tienes un buen trato con los animales. Entiendes sus necesidades casi al instante y eres capaz de cuidarlos como solo ellos necesitan. Lo que te labra una buena amistad con ellos. ";
		String secondaryObjectives = "Estás segura de que algo bueno se puede sacar de todo esto. Tu objetivo es intentar obtener una cura milagrosa que salve al mundo de algún gran mal y poder demostrar que también podrías ser un gran médico. ";

		setDescription(characterPlayer, description, background, specialRules, secondaryObjectives);

		party.addMember(characterPlayer);
	}

	@Test
	public void nerd() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().setGender(Gender.MALE);
		characterPlayer.getInfo().setAge(21);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("mediterranean",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(8);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(4);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(5);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lore", "undead",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lore", "demons",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lore", "fairies",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lore", "weaponsLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 2);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("computing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("disguise",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("search",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);

		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("curious", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("delusional",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("gullible", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("painSensitive",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("clumsy", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros0]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		System.out.println("Nerd " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));
		
		Assert.assertEquals(CostCalculator.getCost(characterPlayer),
				FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.PEACEFUL);
		randomizeCharacter.createCharacter();

		String description = characterPlayer.getCompleteNameRepresentation()
				+ " es un joven amante de los juegos de rol. Lo que le hace ver lo que está ocurriendo de una forma distinta. Gracias a las horas inmersas jugando al D&D 6rd Edition es capaz de ver lo que ha pasado con otros ojos. Para él, esto no es más que otra aventura como las que ya ha vivido entre papeles y lápices.";
		String background = "Como estudiante en una ciudad sin familia te has visto enclaustrado y solo en tu miserable piso de alquiler. Todo lo que has oído se parece mucho a lo que describen tus libros de rol, lo que te ha ayudado en parte a sobrevivir. Cuando esta mañana reventó la puerta de tu casa ese vecino completamente loco y furioso, supiste que tu única salida era usar un cebo. Por lo que saltaste de tu balcón al balcón contiguo de esa pareja de ancianitos que te trataban tan bien. Esperaste escondido en un armario hasta encontrar tu oportunidad. En la oscuridad, encontraste un extraño pendrive de colores blanco, rojo y azul, e instintivamente lo metiste en uno de tus bolsillos ¡Estamos en una película de espías!. Unas horas después la puerta de la casa se abrió y el atacante se distrajo con lo que parecía una persona bastante corpulenta, lo que te permitió escabullirte sin ser visto, y para cuando pudiste escapar del edificio, un autobús te estaba esperando rodeado de militares.";
		String specialRules = "Siempre que quieras obtener información sobre una criatura fantástica y puedas consultar alguno de tus libros de rol puedes hacer una tirada de Saber. Según los PV obtenidos el Director de Juego de aportará más o menos información. Únicamente puedes realizar una tirada por pregunta. ";
		String secondaryObjectives = "Eres el más joven sin duda. Nadie te tiene en cuenta para nada, eres una sombra en el colegio... Esto se va a acabar. Quieres conseguir algo grandioso que te convierta en alguien popular y famoso. Cuantas más cosas logres para ser el centro de atención ¡tanto mejor!";

		setDescription(characterPlayer, description, background, specialRules, secondaryObjectives);

		party.addMember(characterPlayer);
	}

	@Test
	public void teacher() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().setGender(Gender.MALE);
		characterPlayer.getInfo().setAge(31);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("mediterranean",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(8);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(4);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("computing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("landcarft",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("physics",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("chemistry",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("biology",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("etiquette",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("empathy",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("knavery",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);

		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("handsome", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("casanova", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("badLiver", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("cad", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("language [gracefulTongue]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros50]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		System.out.println("Teacher " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));
		
		Assert.assertEquals(CostCalculator.getCost(characterPlayer),
				FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.PEACEFUL);
		randomizeCharacter.createCharacter();

		String description = characterPlayer.getCompleteNameRepresentation() + " es un hombre con buena presencia y saber vestir. Trabaja como profesor de instituto y hace gala de conocimientos en varias materias. Tiene algo que le hace especial, la gente le respeta como profesional y los que le conocen le tratan bien sin saber por qué. Es una persona con carisma y buena presencia, y resulta evidente que incluso en las situaciones más complicadas, cae en gracia a las mujeres.";
		String background = "Durante estos últimos días, has burlado el toque de queda para visitar a una de tus alumnas con las que tienes una relación especial (y algo prohíbida). Debido a tus andanzas nocturnas fuiste detenido hace dos noches por una mujer soldado, si bien pudiste convencerla de que te dejara ir. Durante las pocas horas de duró tu captura, lograste escuchar una conversación por radio en la que quedaba evidente que el ejército estaba reagrupándose en el Parque Tecnológico de la ciudad y se estaban fortificando allí para poder contener algún tipo de extraño peligro.";
		String specialRules = "Siempres que trates con alguien del sexo opuesto, tiene una dificultad de -4 a cualquier tirada que sea para oponerse a una acción contra ti. Esta penalización únicamente se aplica a aquellas mujeres que no tengan una mala relación contigo.";
		String secondaryObjectives = "Tu aspecto y tu presencia son lo más importante para ti. No pudes bajo ningún concepto permitir que este se vea perjudicado, y si fuera el caso, deberás hacer todo lo posible para que tu aspecto sea siempre impecable. No importa lo que cueste o lo que tengas que hacer. No importa lo que cueste o lo que tengas que hacer. No permitirás quedarte en una situación que perjudique tu imagen. ";

		setDescription(characterPlayer, description, background, specialRules, secondaryObjectives);

		party.addMember(characterPlayer);
	}

	@Test
	public void cartomancer() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().setGender(Gender.FEMALE);
		characterPlayer.getInfo().setAge(51);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("arabian", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(8);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(4);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(4);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("gaming",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("lockpicking",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 7);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("streetWise",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sleightOfHand",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("knavery",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("disguise",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("empathy",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("search",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("melee",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);

		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("eloquent", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros1000]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		System.out.println("Cartomancer " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));
		
		Assert.assertEquals(CostCalculator.getCost(characterPlayer),
				FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.PEACEFUL);
		randomizeCharacter.createCharacter();

		String description = characterPlayer.getCompleteNameRepresentation()
				+ " es una mujer algo entrada en años que se gana la vida prediciendo el futuro con las cartas. Ha viajado de una parte a otra del mundo con sus artes y dice que ha ayudado a grandes estrellas de Hollywood para llegar hasta lo más alto gracias a sus dotes adivinatorios. Lleva numerosas pulseras y collares con multitud de piedras preciosas y semipreciosas.";
		String background = "Siempre has sido una timadora y una ladrona, pero los eventos actuales te dan que pensar que algo sobrenatural existe en la ciudad. Has intentado buscar qué o quién está detrás de todo esto, pero a pesar de tus habilidades de sigilo, la fuerte presencia militar te ha impedido descubrir nada. Notas que hay una presencia oscura en la ciudad, y quieres contactar con ella. Lo que aún no has decidido si es para destruirla o unirte a ella...";
		String specialRules = "Eres una maestra de las cartas. Las mueves delante de los ojos de las demás personas con una agilidad asombrosa. Esto provoca una distracción ideal para encubrir tus artimañas. Siempre que utilices tus cartas como cebo, cualquier persona que te esté prestando atención quedará cautivada por tus palabras y movimientos, lo que los distraerá de cualquier otra acción causando un -2 a Percepción mientras dure el engaño.";
		String secondaryObjectives = "Nunca has tenido un auténtico contacto con lo sobrenatural. Pero parece que ahora es un buen momento. Te gustaría pactar o controlar lo que sea que está pasando aquí para poder ser por fin una auténtica hechicera. ";

		setDescription(characterPlayer, description, background, specialRules, secondaryObjectives);

		party.addMember(characterPlayer);
	}

	@Test
	public void dwarf() throws MalformedURLException, DocumentException, IOException, InvalidXmlElementException,
			TooManyBlessingsException, BeneficeAlreadyAddedException, BlessingAlreadyAddedException,
			DuplicatedPreferenceException, InvalidRandomElementSelectedException {
		final CharacterPlayer characterPlayer = new CharacterPlayer(LANGUAGE, MODULE_FOLDER);
		characterPlayer.getInfo().addName(new Name("Peter", characterPlayer.getLanguage(),
				characterPlayer.getModuleName(), Gender.MALE, characterPlayer.getFaction()));
		characterPlayer.getInfo().setSurname(new Surname("Dinklage", characterPlayer.getLanguage(),
				characterPlayer.getModuleName(), characterPlayer.getFaction()));

		characterPlayer.getInfo().setGender(Gender.MALE);
		characterPlayer.getInfo().setAge(78);
		characterPlayer.setRace(RaceFactory.getInstance().getElement("human", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.getInfo().setPlanet(PlanetFactory.getInstance().getElement("habitax",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.setFaction(FactionsFactory.getInstance().getElement("englishSpeaking",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));

		characterPlayer.getCharacteristic(CharacteristicName.STRENGTH).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.DEXTERITY).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.ENDURANCE).setValue(5);
		characterPlayer.getCharacteristic(CharacteristicName.WITS).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.PERCEPTION).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.TECH).setValue(4);
		characterPlayer.getCharacteristic(CharacteristicName.PRESENCE).setValue(7);
		characterPlayer.getCharacteristic(CharacteristicName.WILL).setValue(6);
		characterPlayer.getCharacteristic(CharacteristicName.FAITH).setValue(5);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("vigor",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("observe",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("influence",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 8);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("throwing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("sneak",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 8);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("factionLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("planetaryLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("phoenixEmpireLore",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 5);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("fight",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);

		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("melee",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("empathy",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("archery",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 2);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("arts", "music",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("arts", "drawing",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 1);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("disguise",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 4);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("performance",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 8);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("etiquette",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("leadership",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 6);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("performance",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 8);
		characterPlayer.setSkillRank(AvailableSkillsFactory.getInstance().getElement("gaming",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()), 3);

		characterPlayer.addBenefice(AvailableBeneficeFactory.getInstance().getElement("cash [euros5000]",
				characterPlayer.getLanguage(), characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("dwarf", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));
		characterPlayer.addBlessing(BlessingFactory.getInstance().getElement("wellLiked", characterPlayer.getLanguage(),
				characterPlayer.getModuleName()));

		System.out.println("Dwarf " + CostCalculator.getCost(characterPlayer) + " de "
				+ FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));
		
		Assert.assertEquals(CostCalculator.getCost(characterPlayer),
				FreeStyleCharacterCreation.getFreeAvailablePoints(characterPlayer.getInfo().getAge()));

		// Finalize the character.
		final RandomizeCharacter randomizeCharacter = new RandomizeCharacter(characterPlayer, 0,
				CombatPreferences.PEACEFUL);
		randomizeCharacter.createCharacter();

		String description = characterPlayer.getCompleteNameRepresentation()
				+ " es un actor que antaño tuvo un gran éxito por participar en varias películas y series con un gran éxito. Ya retirado debido a su edad de 78 años, se compró una casita en las afueras de Habitax para vivir lejos de la fama y del ruido de Hollywood. Lo que no esperaba, que su tranquilidad se volviera en tragedia. ";
		String background = "Tu veganismo te ha metido en serias dificultades, ya que la verdura fresca ha sido extremadamente difícil de conseguir. Has tenido que pulular por las sombras de las noches para intentar saquear fruterías o tiendas de comestibles. Has tenido mucha suerte, ya que los militares parecían siempre muy entretenidos persiguiendo a otros objetivos. Durante una de tus incursiones, has encontrado entre unas lechugas unos zarcillos negros que te has llevado a casa. No parecían comestibles pero has descubierto que crecen a un ritmo vertiginoso. Parecían sentirse atraídos por tu viejo radiocasette y en dos noches han crecido lo suficiente para enrollarse alrededor de este.";
		String specialRules = "Pasar desapercibido. Ya sea por los papeles que ha representado, o simplemente por un golpe de suerte, siempre has sido un superviviente nato. Cada vez que haya un encuentro fortuito o alguna criatura ataque al azar a un miembro del grupo, si este personaje obtiene en una tirada un resultado de 13 o menos, será ignorado como víctima y la criaturá habrá decidido que el personaje no resulta peligroso, atacarando a otro miembro del grupo (al jugador de su derecha o izquierda). No tiene efecto si el ataque es dirigido expresamente hacia él (como por ejemplo, si la criatura se está defendiendo de los ataques de "
				+ characterPlayer.getInfo().getNames().get(0) + "). ";
		String secondaryObjectives = "Echas de menos la fama que tuviste antaño cuando eras un actor conocido. Tu objetivo no es solo descubrir lo que sea que está pasando, sino que además, tienes que quedar mejor que el resto para poder atribuirte el mérito en los medios de comunicación. No importa si manipulas, engañas o simplemente huyes para lograr el fin. Sea como sea tienes que ser el mejor a los ojos del mundo.";

		setDescription(characterPlayer, description, background, specialRules, secondaryObjectives);

		party.addMember(characterPlayer);
	}

	@AfterClass
	public void createPartySheet() {
		final PartySheet sheet = new PartySheet(party);
		Assert.assertEquals(
				sheet.createFile(System.getProperty("java.io.tmpdir") + File.separator + party.getPartyName() + ".pdf"),
				20);

		final SmallPartySheet smallSheet = new SmallPartySheet(party);
		Assert.assertEquals(
				smallSheet.createFile(
						System.getProperty("java.io.tmpdir") + File.separator + party.getPartyName() + "_Small.pdf"),
				10);
	}
}
