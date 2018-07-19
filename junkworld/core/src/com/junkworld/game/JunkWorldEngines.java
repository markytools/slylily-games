	package com.junkworld.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class JunkWorldEngines {
//	Current Profile 
	private String profileName = "";
	private int level = 10;
	private float currentXP = 1;
	private float xpLevelUp = 5;
	private int junkCoins = 0;
	private int highestScoreDay = 0;
	private int highestScoreEver = 0;
	private int theme = 0;
	private String currentJob = "none";
	private int gameSelection = 1;
	private int fee;
	
	private int gameMode = 1;
	private Array<Float> spawnTime;
	private int dumpsterCapacity = 25;
	private float dumpingSpeed = 0.5f;
	private float trashAccerleration = .05f;
	private int gameTimer = 120, maxTrashNotDumped = 10;
	private float vacuumSuctionSpeed = 0.5f;
	private float trashBlowerBlowSpeed = 0.005f; 
//	blower in percentage of initialSpeed Reduction
	private long burstModeCooldown = 16000, burstModeDur = 2500;
	private float swiftCanDegen = 2f, swiftCanEnergyTot = 5f;
	private int garbageGoal;
	private int garbageAmount = 0;

	private int currentBackgroundMusic = 1;
	
	private boolean kitchen1Unlocked = true, kitchen2Unlocked = true, kitchen3Unlocked = true, kitchen4Unlocked = true, kitchen5Unlocked = true,
		basement1Unlocked = true, basement2Unlocked = true, basement3Unlocked = true, basement4Unlocked = true, basement5Unlocked = true,
		garage1Unlocked = true, garage2Unlocked = true, garage3Unlocked = true, garage4Unlocked = true, garage5Unlocked = true,
		backyard1Unlocked = true, backyard2Unlocked = true, backyard3Unlocked = true, backyard4Unlocked = true, backyard5Unlocked = true,
		sidewalk1Unlocked = true, sidewalk2Unlocked = true, sidewalk3Unlocked = true, sidewalk4Unlocked = true, sidewalk5Unlocked = true,
		factory1Unlocked = true, factory2Unlocked = true, factory3Unlocked = true, factory4Unlocked = true, factory5Unlocked = true,
		highway1Unlocked = true, highway2Unlocked = true, highway3Unlocked = true, highway4Unlocked = true, highway5Unlocked = true,
		park1Unlocked = true, park2Unlocked = true, park3Unlocked = true, park4Unlocked = true, park5Unlocked = true,
		sewer1Unlocked = true, sewer2Unlocked = true, sewer3Unlocked = true, sewer4Unlocked = true, sewer5Unlocked = true;
	
	private boolean trashSpawnRow2 = false, trashSpawnRow3 = false, trashSpawnRow4 = false;
	
//	previousColors
	private int previousPalette1;
	private int previousPalette2;
	private int previousPalette3;
	private int previousPalette4;
	private int averageCanCapacity = 9, scorchingCanCapacity = 7, vacuumCanCapacity = 7, trashBlowerCapacity = 7, dullCanCapacity = 5, swiftCanCapacity = 3;

	private TrashCan1Properties trashCan1Properties;
	private TrashCan2Properties trashCan2Properties;
	private TrashCan3Properties trashCan3Properties;
	private TrashCan4Properties trashCan4Properties;
	private GamePalette gamePalette;
	
	private float xPos;
	private boolean greaterThanXPos;
	private boolean lessThanXPos;
	private boolean isOnPosX;
	private int canSelected = 0;
	private boolean isFlipped;
	private boolean trashCanSelected;
	private boolean clickableCan = true;
	private boolean clickableCol = false;
	private boolean cancelMove = false;
	private float backXPos;
	private boolean greaterThanReturningXPos, lessThanReturningXPos;
	
	private boolean occupiedTrashCell1 = false, occupiedTrashCell2 = false,
			occupiedTrashCell3 = false, occupiedTrashCell4 = false,
			occupiedTrashCell5 = false, occupiedTrashCell6 = false,
			occupiedTrashCell7 = false, occupiedTrashCell8 = false,
			occupiedTrashCell9 = false, occupiedTrashCell10 = false,
			occupiedTrashCell11 = false, occupiedTrashCell12 = false,
			occupiedTrashCell13 = false, occupiedTrashCell14 = false,
			occupiedTrashCell15 = false, occupiedTrashCell16 = false;
	
	private boolean houseUnlocked = true, cityUnlocked = true, forestUnlocked = false;
	
	private boolean kitchenUnlocked = false, basementUnlocked = false, garageUnlocked = false, backyardUnlocked = false, sidewalkUnlocked = false,
			factoryUnlocked = false, highwayUnlocked = false, parkUnlocked = false, sewerUnlocked = false;
	
	private int totalUnlockedItemsSlots = 2;
	
	private boolean palette1Unlocked = true, palette2Unlocked = true, palette3Unlocked = false, palette4Unlocked = false;

	private boolean averageCanUnlocked = true, scorchingCanUnlocked = false, vacuumCanUnlocked = false, trashBlowerUnlocked = false, dullCanUnlocked = false,
			swiftCanUnlocked = false;
	
	private boolean averageSkill1Unlocked = true, averageSkill2Unlocked = true, averageSkill3Unlocked = true, averageSkill4Unlocked = true;
	private boolean scorchingSkill1Unlocked = true, scorchingSkill2Unlocked = true, scorchingSkill3Unlocked = true, scorchingSkill4Unlocked = true;
	private boolean vacuumSkill1Unlocked = true, vacuumSkill2Unlocked = true, vacuumSkill3Unlocked = true, vacuumSkill4Unlocked = true;
	private boolean blowerSkill1Unlocked = false, blowerSkill2Unlocked = false, blowerSkill3Unlocked = false, blowerSkill4Unlocked = false;
	private boolean dullSkill1Unlocked = false, dullSkill2Unlocked = false, dullSkill3Unlocked = false, dullSkill4Unlocked = false;
	private boolean swiftSkill1Unlocked = false, swiftSkill2Unlocked = false, swiftSkill3Unlocked = false, swiftSkill4Unlocked = false;
	
	private boolean bomb = true, glue = true, iceflake = false, switchMachine = false, augmentedBurst = false, sticker = false, flyswatter = false, electricZapper = false, accelerator = false, acceleratorX = false, steelFlyswatter = false,
			itemRestore = false, superGlue = false, windVane = false;
	
	private boolean acorn = false, apple = false, bananaPeel = false, branch = false, deadMouse = false, eggShell = false, feather = false, fishBone = false, flower = false,
			grass = false, hair = false, hay = false, leaves = false, manure = false, roots = false;
	private boolean bottle = false, can = false, cardboard = false, cerealBox = false, dirtyShirt = false, envelope = false, funnel = false, hanger = false, newspaper = false,
			paper = false, pillBottle = false, plasticBag = false, tyre = false, vase = false, waffleIron = false;
	private boolean aerosolCan = false, brokenBulb = false, brokenGlass = false, chewingGum = false, cigarette = false, deadBattery = false, dirtyDiaper = false,
			insecticideSpray = false, leftoverCake = false, leftoverChicken = false, nail = false, paintCan = false, syringe = false, tornPaper = false, usedMotorOil = false;

//	JunkWorld Album
	private boolean acornAlbum = true, appleAlbum = true, bananaPeelAlbum = true, branchAlbum = true, deadMouseAlbum = true, eggShellAlbum = true, featherAlbum = true, 
			fishBoneAlbum = true, flowerAlbum = true, grassAlbum = true, hairAlbum = true, hayAlbum = true, leavesAlbum = true, manureAlbum = true, rootsAlbum = true;
	private boolean bottleAlbum = true, canAlbum = true, cardboardAlbum = true, cerealBoxAlbum = true, dirtyShirtAlbum = true, envelopeAlbum = true, funnelAlbum = true,
			hangerAlbum = true, newspaperAlbum = true, paperAlbum = true, pillBottleAlbum = true, plasticBagAlbum = true, tyreAlbum = true, vaseAlbum = true,
			waffleIronAlbum = true;
	private boolean aerosolCanAlbum = true, brokenBulbAlbum = true, brokenGlassAlbum = true, chewingGumAlbum = true, cigaretteAlbum = true, deadBatteryAlbum = true,
			dirtyDiaperAlbum = true, insecticideSprayAlbum = true, leftoverCakeAlbum = true, leftoverChickenAlbum = true, nailAlbum = true, paintCanAlbum = true, 
			syringeAlbum = true, tornPaperAlbum = true, usedMotorOilAlbum = true;
	
	private boolean red = true, orange = true, yellow = true, green = true, blue = true, purple = true;
	
	private boolean biodegradable = true, recyclable = true, nonRecyclable = true;
	
	private int acornValue = 1, appleValue = 1, bananaPeelValue = 1, branchValue = 1, deadMouseValue = 1, eggShellValue = 1, featherValue = 1, fishBoneValue = 1,
			flowerValue = 1, grassValue = 1, hairValue = 1, hayValue = 1, leavesValue = 1, manureValue = 1, rootsValue = 1;
	private int bottleValue = 1, canValue = 1, cardboardValue = 1, cerealBoxValue = 1, dirtyShirtValue = 1, envelopeValue = 1, funnelValue = 1, hangerValue = 1,
			newspaperValue = 1,	paperValue = 1, pillBottleValue = 1, plasticBagValue = 1, tyreValue = 1, vaseValue = 1, waffleIronValue = 1;
	private int aerosolCanValue = 1, brokenBulbValue = 1, brokenGlassValue = 1, chewingGumValue = 1, cigaretteValue = 1, deadBatteryValue = 1, dirtyDiaperValue = 1,
			insecticideSprayValue = 1, leftoverCakeValue = 1, leftoverChickenValue = 1, nailValue = 1, paintCanValue = 1, syringeValue = 1, tornPaperValue = 1,
			usedMotorOilValue = 1;
	
	private int previousTrashCan1Color, previousTrashCan1Type, previousTrashCan2Color, previousTrashCan2Type, previousTrashCan3Color, previousTrashCan3Type,
	previousTrashCan4Color, previousTrashCan4Type;
	
	private int dullPrevColor1, dullPrevColor2, dullPrevColor3, dullPrevColor4;
	
	private int currentDifficulty;
	
//	Tutorials Section
	private Array<Texture> tutorial;
	private Array<Texture> tutorialArrow;
	private Rectangle tutorialLayer;
	private Stage tutorialStage;
	private Actor quitTutorial;
	private Actor clickContinueTutorial;
	private int currentTutorial = 0, currentArrowNum = 0;
	private Sprite currentArrow;
	private long delayArrow;
//	TODO
	
	public JunkWorldEngines(){
		
	}

	public float getXPos() {
		return xPos;
	}

	public void setXPos(float xPos) {
		this.xPos = xPos;
	}

	public boolean isGreaterThanXPos() {
		return greaterThanXPos;
	}

	public void setGreaterThanXPos(boolean greaterThanXPos) {
		this.greaterThanXPos = greaterThanXPos;
	}

	public boolean isLessThanXPos() {
		return lessThanXPos;
	}

	public void setLessThanXPos(boolean lesserThanXPos) {
		this.lessThanXPos = lesserThanXPos;
	}

	public boolean isOnPosX() {
		return isOnPosX;
	}

	public void setOnPosX(boolean isOnPosX) {
		this.isOnPosX = isOnPosX;
	}

	public boolean isClickableCol() {
		return clickableCol;
	}

	public void setClickableCol(boolean clickableCol) {
		this.clickableCol = clickableCol;
	}

	public int getCanSelected() {
		return canSelected;
	}

	public void setCanSelected(int canSelected) {
		this.canSelected = canSelected;
	}

	public boolean isFlipped() {
		return isFlipped;
	}

	public void setFlipped(boolean isFlipped) {
		this.isFlipped = isFlipped;
	}

	public boolean isTrashCanSelected() {
		return trashCanSelected;
	}

	public void setTrashCanSelected(boolean trashCanSelected) {
		this.trashCanSelected = trashCanSelected;
	}

	public boolean isClickableCan() {
		return clickableCan;
	}

	public void setClickableCan(boolean clickableCan) {
		this.clickableCan = clickableCan;
	}

	public boolean isCancelMove() {
		return cancelMove;
	}

	public void setCancelMove(boolean cancelMove) {
		this.cancelMove = cancelMove;
	}

	public float getBackXPos() {
		return backXPos;
	}

	public void setBackXPos(float backXPos) {
		this.backXPos = backXPos;
	}

	public boolean isGreaterThanReturningXPos() {
		return greaterThanReturningXPos;
	}

	public void setGreaterThanReturningXPos(boolean greaterThanReturningXPos) {
		this.greaterThanReturningXPos = greaterThanReturningXPos;
	}

	public boolean isOccupiedTrashCell1() {
		return occupiedTrashCell1;
	}

	public void setOccupiedTrashCell1(boolean occupiedTrashCell1) {
		this.occupiedTrashCell1 = occupiedTrashCell1;
	}

	public boolean isOccupiedTrashCell2() {
		return occupiedTrashCell2;
	}

	public void setOccupiedTrashCell2(boolean occupiedTrashCell2) {
		this.occupiedTrashCell2 = occupiedTrashCell2;
	}

	public boolean isOccupiedTrashCell3() {
		return occupiedTrashCell3;
	}

	public void setOccupiedTrashCell3(boolean occupiedTrashCell3) {
		this.occupiedTrashCell3 = occupiedTrashCell3;
	}

	public boolean isOccupiedTrashCell4() {
		return occupiedTrashCell4;
	}

	public void setOccupiedTrashCell4(boolean occupiedTrashCell4) {
		this.occupiedTrashCell4 = occupiedTrashCell4;
	}

	public boolean isOccupiedTrashCell5() {
		return occupiedTrashCell5;
	}

	public void setOccupiedTrashCell5(boolean occupiedTrashCell5) {
		this.occupiedTrashCell5 = occupiedTrashCell5;
	}

	public boolean isOccupiedTrashCell6() {
		return occupiedTrashCell6;
	}

	public void setOccupiedTrashCell6(boolean occupiedTrashCell6) {
		this.occupiedTrashCell6 = occupiedTrashCell6;
	}

	public boolean isOccupiedTrashCell7() {
		return occupiedTrashCell7;
	}

	public void setOccupiedTrashCell7(boolean occupiedTrashCell7) {
		this.occupiedTrashCell7 = occupiedTrashCell7;
	}

	public boolean isOccupiedTrashCell8() {
		return occupiedTrashCell8;
	}

	public void setOccupiedTrashCell8(boolean occupiedTrashCell8) {
		this.occupiedTrashCell8 = occupiedTrashCell8;
	}

	public boolean isOccupiedTrashCell9() {
		return occupiedTrashCell9;
	}

	public void setOccupiedTrashCell9(boolean occupiedTrashCell9) {
		this.occupiedTrashCell9 = occupiedTrashCell9;
	}

	public boolean isOccupiedTrashCell10() {
		return occupiedTrashCell10;
	}

	public void setOccupiedTrashCell10(boolean occupiedTrashCell10) {
		this.occupiedTrashCell10 = occupiedTrashCell10;
	}

	public boolean isOccupiedTrashCell11() {
		return occupiedTrashCell11;
	}

	public void setOccupiedTrashCell11(boolean occupiedTrashCell11) {
		this.occupiedTrashCell11 = occupiedTrashCell11;
	}

	public boolean isOccupiedTrashCell12() {
		return occupiedTrashCell12;
	}

	public void setOccupiedTrashCell12(boolean occupiedTrashCell12) {
		this.occupiedTrashCell12 = occupiedTrashCell12;
	}

	public boolean isOccupiedTrashCell13() {
		return occupiedTrashCell13;
	}

	public void setOccupiedTrashCell13(boolean occupiedTrashCell13) {
		this.occupiedTrashCell13 = occupiedTrashCell13;
	}

	public boolean isOccupiedTrashCell14() {
		return occupiedTrashCell14;
	}

	public void setOccupiedTrashCell14(boolean occupiedTrashCell14) {
		this.occupiedTrashCell14 = occupiedTrashCell14;
	}

	public boolean isOccupiedTrashCell15() {
		return occupiedTrashCell15;
	}

	public void setOccupiedTrashCell15(boolean occupiedTrashCell15) {
		this.occupiedTrashCell15 = occupiedTrashCell15;
	}

	public boolean isOccupiedTrashCell16() {
		return occupiedTrashCell16;
	}

	public void setOccupiedTrashCell16(boolean occupiedTrashCel16) {
		this.occupiedTrashCell16 = occupiedTrashCel16;
	}

	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public boolean isPalette1Unlocked() {
		return palette1Unlocked;
	}

	public void setPalette1Unlocked(boolean palette1Unlocked) {
		this.palette1Unlocked = palette1Unlocked;
	}

	public boolean isPalette2Unlocked() {
		return palette2Unlocked;
	}

	public void setPalette2Unlocked(boolean palette2Unlocked) {
		this.palette2Unlocked = palette2Unlocked;
	}

	public boolean isPalette3Unlocked() {
		return palette3Unlocked;
	}

	public void setPalette3Unlocked(boolean palette3Unlocked) {
		this.palette3Unlocked = palette3Unlocked;
	}

	public boolean isPalette4Unlocked() {
		return palette4Unlocked;
	}

	public void setPalette4Unlocked(boolean palette4Unlocked) {
		this.palette4Unlocked = palette4Unlocked;
	}

	public boolean isAverageSkill1Unlocked() {
		return averageSkill1Unlocked;
	}

	public void setAverageSkill1Unlocked(boolean averageSkill1Unlocked) {
		this.averageSkill1Unlocked = averageSkill1Unlocked;
	}

	public boolean isAverageSkill2Unlocked() {
		return averageSkill2Unlocked;
	}

	public void setAverageSkill2Unlocked(boolean averageSkill2Unlocked) {
		this.averageSkill2Unlocked = averageSkill2Unlocked;
	}

	public boolean isAverageSkill3Unlocked() {
		return averageSkill3Unlocked;
	}

	public void setAverageSkill3Unlocked(boolean averageSkill3Unlocked) {
		this.averageSkill3Unlocked = averageSkill3Unlocked;
	}

	public boolean isAverageSkill4Unlocked() {
		return averageSkill4Unlocked;
	}

	public void setAverageSkill4Unlocked(boolean averageSkill4Unlocked) {
		this.averageSkill4Unlocked = averageSkill4Unlocked;
	}

	public boolean isScorchingSkill1Unlocked() {
		return scorchingSkill1Unlocked;
	}

	public void setScorchingSkill1Unlocked(boolean scorchingSkill1Unlocked) {
		this.scorchingSkill1Unlocked = scorchingSkill1Unlocked;
	}

	public boolean isScorchingSkill2Unlocked() {
		return scorchingSkill2Unlocked;
	}

	public void setScorchingSkill2Unlocked(boolean scorchingSkill2Unlocked) {
		this.scorchingSkill2Unlocked = scorchingSkill2Unlocked;
	}

	public boolean isScorchingSkill3Unlocked() {
		return scorchingSkill3Unlocked;
	}

	public void setScorchingSkill3Unlocked(boolean scorchingSkill3Unlocked) {
		this.scorchingSkill3Unlocked = scorchingSkill3Unlocked;
	}

	public boolean isScorchingSkill4Unlocked() {
		return scorchingSkill4Unlocked;
	}

	public void setScorchingSkill4Unlocked(boolean scorchingSkill4Unlocked) {
		this.scorchingSkill4Unlocked = scorchingSkill4Unlocked;
	}

	public boolean isVacuumSkill1Unlocked() {
		return vacuumSkill1Unlocked;
	}

	public void setVacuumSkill1Unlocked(boolean vacuumSkill1Unlocked) {
		this.vacuumSkill1Unlocked = vacuumSkill1Unlocked;
	}

	public boolean isVacuumSkill2Unlocked() {
		return vacuumSkill2Unlocked;
	}

	public void setVacuumSkill2Unlocked(boolean vacuumSkill2Unlocked) {
		this.vacuumSkill2Unlocked = vacuumSkill2Unlocked;
	}

	public boolean isVacuumSkill3Unlocked() {
		return vacuumSkill3Unlocked;
	}

	public void setVacuumSkill3Unlocked(boolean vacuumSkill3Unlocked) {
		this.vacuumSkill3Unlocked = vacuumSkill3Unlocked;
	}

	public boolean isVacuumSkill4Unlocked() {
		return vacuumSkill4Unlocked;
	}

	public void setVacuumSkill4Unlocked(boolean vacuumSkill4Unlocked) {
		this.vacuumSkill4Unlocked = vacuumSkill4Unlocked;
	}

	public boolean isBomb() {
		return bomb;
	}

	public void setBomb(boolean bomb) {
		this.bomb = bomb;
	}

	public boolean isFlyswatter() {
		return flyswatter;
	}

	public void setFlyswatter(boolean flyswatter) {
		this.flyswatter = flyswatter;
	}

	public boolean isIceflake() {
		return iceflake;
	}

	public void setIceflake(boolean iceflake) {
		this.iceflake = iceflake;
	}

	public boolean isElectricZapper() {
		return electricZapper;
	}

	public void setElectricZapper(boolean electricZapper) {
		this.electricZapper = electricZapper;
	}

	public boolean isAccelerator() {
		return accelerator;
	}

	public void setAccelerator(boolean accelerator) {
		this.accelerator = accelerator;
	}

	public boolean isAcceleratorX() {
		return acceleratorX;
	}

	public void setAcceleratorX(boolean acceleratorX) {
		this.acceleratorX = acceleratorX;
	}

	public boolean isSteelFlyswatter() {
		return steelFlyswatter;
	}

	public void setSteelFlyswatter(boolean steelFlyswatter) {
		this.steelFlyswatter = steelFlyswatter;
	}

	public boolean isGlue() {
		return glue;
	}

	public void setGlue(boolean glue) {
		this.glue = glue;
	}

	public boolean isItemRestore() {
		return itemRestore;
	}

	public void setItemRestore(boolean itemRestore) {
		this.itemRestore = itemRestore;
	}

	public boolean isSuperGlue() {
		return superGlue;
	}

	public void setSuperGlue(boolean superGlue) {
		this.superGlue = superGlue;
	}

	public boolean isWindVane() {
		return windVane;
	}

	public void setWindVane(boolean windVane) {
		this.windVane = windVane;
	}

	public int getTotalUnlockedItemsSlots() {
		return totalUnlockedItemsSlots;
	}

	public void setTotalUnlockedItemsSlots(int totalUnlockedItemsSlots) {
		this.totalUnlockedItemsSlots = totalUnlockedItemsSlots;
	}

	public boolean isAcorn() {
		return acorn;
	}

	public void setAcorn(boolean acorn) {
		this.acorn = acorn;
	}

	public boolean isApple() {
		return apple;
	}

	public void setApple(boolean apple) {
		this.apple = apple;
	}

	public boolean isBananaPeel() {
		return bananaPeel;
	}

	public void setBananaPeel(boolean bananaPeel) {
		this.bananaPeel = bananaPeel;
	}

	public boolean isBranch() {
		return branch;
	}

	public void setBranch(boolean branch) {
		this.branch = branch;
	}

	public boolean isDeadMouse() {
		return deadMouse;
	}

	public void setDeadMouse(boolean deadMouse) {
		this.deadMouse = deadMouse;
	}

	public boolean isEggShell() {
		return eggShell;
	}

	public void setEggShell(boolean eggShell) {
		this.eggShell = eggShell;
	}

	public boolean isFeather() {
		return feather;
	}

	public void setFeather(boolean feather) {
		this.feather = feather;
	}

	public boolean isFishBone() {
		return fishBone;
	}

	public void setFishBone(boolean fishBone) {
		this.fishBone = fishBone;
	}

	public boolean isFlower() {
		return flower;
	}

	public void setFlower(boolean flower) {
		this.flower = flower;
	}

	public boolean isGrass() {
		return grass;
	}

	public void setGrass(boolean grass) {
		this.grass = grass;
	}

	public boolean isHair() {
		return hair;
	}

	public void setHair(boolean hair) {
		this.hair = hair;
	}

	public boolean isHay() {
		return hay;
	}

	public void setHay(boolean hay) {
		this.hay = hay;
	}

	public boolean isLeaves() {
		return leaves;
	}

	public void setLeaves(boolean leaves) {
		this.leaves = leaves;
	}

	public boolean isManure() {
		return manure;
	}

	public void setManure(boolean manure) {
		this.manure = manure;
	}

	public boolean isRoots() {
		return roots;
	}

	public void setRoots(boolean roots) {
		this.roots = roots;
	}

	public boolean isBottle() {
		return bottle;
	}

	public void setBottle(boolean bottle) {
		this.bottle = bottle;
	}

	public boolean isCan() {
		return can;
	}

	public void setCan(boolean can) {
		this.can = can;
	}

	public boolean isCardboard() {
		return cardboard;
	}

	public void setCardboard(boolean cardboard) {
		this.cardboard = cardboard;
	}

	public boolean isCerealBox() {
		return cerealBox;
	}

	public void setCerealBox(boolean cerealBox) {
		this.cerealBox = cerealBox;
	}

	public boolean isDirtyShirt() {
		return dirtyShirt;
	}

	public void setDirtyShirt(boolean dirtyShirt) {
		this.dirtyShirt = dirtyShirt;
	}

	public boolean isEnvelope() {
		return envelope;
	}

	public void setEnvelope(boolean envelope) {
		this.envelope = envelope;
	}

	public boolean isFunnel() {
		return funnel;
	}

	public void setFunnel(boolean funnel) {
		this.funnel = funnel;
	}

	public boolean isHanger() {
		return hanger;
	}

	public void setHanger(boolean hanger) {
		this.hanger = hanger;
	}

	public boolean isNewspaper() {
		return newspaper;
	}

	public void setNewspaper(boolean newspaper) {
		this.newspaper = newspaper;
	}

	public boolean isPaper() {
		return paper;
	}

	public void setPaper(boolean paper) {
		this.paper = paper;
	}

	public boolean isPillBottle() {
		return pillBottle;
	}

	public void setPillBottle(boolean pillBottle) {
		this.pillBottle = pillBottle;
	}

	public boolean isPlasticBag() {
		return plasticBag;
	}

	public void setPlasticBag(boolean plasticBag) {
		this.plasticBag = plasticBag;
	}

	public boolean isTyre() {
		return tyre;
	}

	public void setTyre(boolean tyre) {
		this.tyre = tyre;
	}

	public boolean isVase() {
		return vase;
	}

	public void setVase(boolean vase) {
		this.vase = vase;
	}

	public boolean isWaffleIron() {
		return waffleIron;
	}

	public void setWaffleIron(boolean waffleIron) {
		this.waffleIron = waffleIron;
	}

	public boolean isAerosolCan() {
		return aerosolCan;
	}

	public void setAerosolCan(boolean aerosolCan) {
		this.aerosolCan = aerosolCan;
	}

	public boolean isBrokenBulb() {
		return brokenBulb;
	}

	public void setBrokenBulb(boolean brokenBulb) {
		this.brokenBulb = brokenBulb;
	}

	public boolean isBrokenGlass() {
		return brokenGlass;
	}

	public void setBrokenGlass(boolean brokenGlass) {
		this.brokenGlass = brokenGlass;
	}

	public boolean isChewingGum() {
		return chewingGum;
	}

	public void setChewingGum(boolean chewingGum) {
		this.chewingGum = chewingGum;
	}

	public boolean isCigarette() {
		return cigarette;
	}

	public void setCigarette(boolean cigarette) {
		this.cigarette = cigarette;
	}

	public boolean isDeadBattery() {
		return deadBattery;
	}

	public void setDeadBattery(boolean deadBattery) {
		this.deadBattery = deadBattery;
	}

	public boolean isDirtyDiaper() {
		return dirtyDiaper;
	}

	public void setDirtyDiaper(boolean dirtyDiaper) {
		this.dirtyDiaper = dirtyDiaper;
	}

	public boolean isInsecticideSpray() {
		return insecticideSpray;
	}

	public void setInsecticideSpray(boolean insecticideSpray) {
		this.insecticideSpray = insecticideSpray;
	}

	public boolean isLeftoverCake() {
		return leftoverCake;
	}

	public void setLeftoverCake(boolean leftoverCake) {
		this.leftoverCake = leftoverCake;
	}

	public boolean isLeftoverChicken() {
		return leftoverChicken;
	}

	public void setLeftoverChicken(boolean leftoverChicken) {
		this.leftoverChicken = leftoverChicken;
	}

	public boolean isNail() {
		return nail;
	}

	public void setNail(boolean nail) {
		this.nail = nail;
	}

	public boolean isPaintCan() {
		return paintCan;
	}

	public void setPaintCan(boolean paintCan) {
		this.paintCan = paintCan;
	}

	public boolean isSyringe() {
		return syringe;
	}

	public void setSyringe(boolean syringe) {
		this.syringe = syringe;
	}

	public boolean isTornPaper() {
		return tornPaper;
	}

	public void setTornPaper(boolean tornPaper) {
		this.tornPaper = tornPaper;
	}

	public boolean isUsedMotorOil() {
		return usedMotorOil;
	}

	public void setUsedMotorOil(boolean usedMotorOil) {
		this.usedMotorOil = usedMotorOil;
	}

	public float getTrashAccerleration() {
		return trashAccerleration;
	}

	public void setTrashAccerleration(float trashAccerleration) {
		this.trashAccerleration = trashAccerleration;
	}

	public int getAcornValue() {
		return acornValue;
	}

	public void setAcornValue(int acornValue) {
		this.acornValue = acornValue;
	}

	public int getAppleValue() {
		return appleValue;
	}

	public void setAppleValue(int appleValue) {
		this.appleValue = appleValue;
	}

	public int getBananaPeelValue() {
		return bananaPeelValue;
	}

	public void setBananaPeelValue(int bananaPeelValue) {
		this.bananaPeelValue = bananaPeelValue;
	}

	public int getBranchValue() {
		return branchValue;
	}

	public void setBranchValue(int branchValue) {
		this.branchValue = branchValue;
	}

	public int getDeadMouseValue() {
		return deadMouseValue;
	}

	public void setDeadMouseValue(int deadMouseValue) {
		this.deadMouseValue = deadMouseValue;
	}

	public int getEggShellValue() {
		return eggShellValue;
	}

	public void setEggShellValue(int eggShellValue) {
		this.eggShellValue = eggShellValue;
	}

	public int getFeatherValue() {
		return featherValue;
	}

	public void setFeatherValue(int featherValue) {
		this.featherValue = featherValue;
	}

	public int getFishBoneValue() {
		return fishBoneValue;
	}

	public void setFishBoneValue(int fishBoneValue) {
		this.fishBoneValue = fishBoneValue;
	}

	public int getFlowerValue() {
		return flowerValue;
	}

	public void setFlowerValue(int flowerValue) {
		this.flowerValue = flowerValue;
	}

	public int getGrassValue() {
		return grassValue;
	}

	public void setGrassValue(int grassValue) {
		this.grassValue = grassValue;
	}

	public int getHairValue() {
		return hairValue;
	}

	public void setHairValue(int hairValue) {
		this.hairValue = hairValue;
	}

	public int getHayValue() {
		return hayValue;
	}

	public void setHayValue(int hayValue) {
		this.hayValue = hayValue;
	}

	public int getLeavesValue() {
		return leavesValue;
	}

	public void setLeavesValue(int leavesValue) {
		this.leavesValue = leavesValue;
	}

	public int getManureValue() {
		return manureValue;
	}

	public void setManureValue(int manureValue) {
		this.manureValue = manureValue;
	}

	public int getRootsValue() {
		return rootsValue;
	}

	public void setRootsValue(int rootsValue) {
		this.rootsValue = rootsValue;
	}

	public int getBottleValue() {
		return bottleValue;
	}

	public void setBottleValue(int bottleValue) {
		this.bottleValue = bottleValue;
	}

	public int getCanValue() {
		return canValue;
	}

	public void setCanValue(int canValue) {
		this.canValue = canValue;
	}

	public int getCardboardValue() {
		return cardboardValue;
	}

	public void setCardboardValue(int cardboardValue) {
		this.cardboardValue = cardboardValue;
	}

	public int getCerealBoxValue() {
		return cerealBoxValue;
	}

	public void setCerealBoxValue(int cerealBoxValue) {
		this.cerealBoxValue = cerealBoxValue;
	}

	public int getDirtyShirtValue() {
		return dirtyShirtValue;
	}

	public void setDirtyShirtValue(int dirtyShirtValue) {
		this.dirtyShirtValue = dirtyShirtValue;
	}

	public int getEnvelopeValue() {
		return envelopeValue;
	}

	public void setEnvelopeValue(int envelopeValue) {
		this.envelopeValue = envelopeValue;
	}

	public int getFunnelValue() {
		return funnelValue;
	}

	public void setFunnelValue(int funnelValue) {
		this.funnelValue = funnelValue;
	}

	public int getHangerValue() {
		return hangerValue;
	}

	public void setHangerValue(int hangerValue) {
		this.hangerValue = hangerValue;
	}

	public int getNewspaperValue() {
		return newspaperValue;
	}

	public void setNewspaperValue(int newspaperValue) {
		this.newspaperValue = newspaperValue;
	}

	public int getPaperValue() {
		return paperValue;
	}

	public void setPaperValue(int paperValue) {
		this.paperValue = paperValue;
	}

	public int getPillBottleValue() {
		return pillBottleValue;
	}

	public void setPillBottleValue(int pillBottleValue) {
		this.pillBottleValue = pillBottleValue;
	}

	public int getPlasticBagValue() {
		return plasticBagValue;
	}

	public void setPlasticBagValue(int plasticBagValue) {
		this.plasticBagValue = plasticBagValue;
	}

	public int getTyreValue() {
		return tyreValue;
	}

	public void setTyreValue(int tyreValue) {
		this.tyreValue = tyreValue;
	}

	public int getVaseValue() {
		return vaseValue;
	}

	public void setVaseValue(int vaseValue) {
		this.vaseValue = vaseValue;
	}

	public int getWaffleIronValue() {
		return waffleIronValue;
	}

	public void setWaffleIronValue(int waffleIronValue) {
		this.waffleIronValue = waffleIronValue;
	}

	public int getAerosolCanValue() {
		return aerosolCanValue;
	}

	public void setAerosolCanValue(int aerosolCanValue) {
		this.aerosolCanValue = aerosolCanValue;
	}

	public int getBrokenBulbValue() {
		return brokenBulbValue;
	}

	public void setBrokenBulbValue(int brokenBulbValue) {
		this.brokenBulbValue = brokenBulbValue;
	}

	public int getBrokenGlassValue() {
		return brokenGlassValue;
	}

	public void setBrokenGlassValue(int brokenGlassValue) {
		this.brokenGlassValue = brokenGlassValue;
	}

	public int getChewingGumValue() {
		return chewingGumValue;
	}

	public void setChewingGumValue(int chewingGumValue) {
		this.chewingGumValue = chewingGumValue;
	}

	public int getCigaretteValue() {
		return cigaretteValue;
	}

	public void setCigaretteValue(int cigaretteValue) {
		this.cigaretteValue = cigaretteValue;
	}

	public int getDeadBatteryValue() {
		return deadBatteryValue;
	}

	public void setDeadBatteryValue(int deadBatteryValue) {
		this.deadBatteryValue = deadBatteryValue;
	}

	public int getDirtyDiaperValue() {
		return dirtyDiaperValue;
	}

	public void setDirtyDiaperValue(int dirtyDiaperValue) {
		this.dirtyDiaperValue = dirtyDiaperValue;
	}

	public int getInsecticideSprayValue() {
		return insecticideSprayValue;
	}

	public void setInsecticideSprayValue(int insecticideSprayValue) {
		this.insecticideSprayValue = insecticideSprayValue;
	}

	public int getLeftoverCakeValue() {
		return leftoverCakeValue;
	}

	public void setLeftoverCakeValue(int leftoverCakeValue) {
		this.leftoverCakeValue = leftoverCakeValue;
	}

	public int getLeftoverChickenValue() {
		return leftoverChickenValue;
	}

	public void setLeftoverChickenValue(int leftoverChickenValue) {
		this.leftoverChickenValue = leftoverChickenValue;
	}

	public int getNailValue() {
		return nailValue;
	}

	public void setNailValue(int nailValue) {
		this.nailValue = nailValue;
	}

	public int getPaintCanValue() {
		return paintCanValue;
	}

	public void setPaintCanValue(int paintCanValue) {
		this.paintCanValue = paintCanValue;
	}

	public int getSyringeValue() {
		return syringeValue;
	}

	public void setSyringeValue(int syringeValue) {
		this.syringeValue = syringeValue;
	}

	public int getTornPaperValue() {
		return tornPaperValue;
	}

	public void setTornPaperValue(int tornPaperValue) {
		this.tornPaperValue = tornPaperValue;
	}

	public int getUsedMotorOilValue() {
		return usedMotorOilValue;
	}

	public void setUsedMotorOilValue(int usedMotorOilValue) {
		this.usedMotorOilValue = usedMotorOilValue;
	}

	public int getDumpsterCapacity() {
		return dumpsterCapacity;
	}

	public void setDumpsterCapacity(int dumpsterCapacity) {
		this.dumpsterCapacity = dumpsterCapacity;
	}

	public float getDumpingSpeed() {
		return dumpingSpeed;
	}

	public void setDumpingSpeed(float dumpingSpeed) {
		this.dumpingSpeed = dumpingSpeed;
	}

	public int getGameTimer() {
		return gameTimer;
	}

	public void setGameTimer(int gameTimer) {
		this.gameTimer = gameTimer;
	}

	public int getMaxTrashNotDumped() {
		return maxTrashNotDumped;
	}

	public void setMaxTrashNotDumped(int maxTrashNotDumped) {
		this.maxTrashNotDumped = maxTrashNotDumped;
	}

	public int getPreviousPalette1() {
		return previousPalette1;
	}

	public void setPreviousPalette1(int previousPalette1) {
		this.previousPalette1 = previousPalette1;
	}

	public int getPreviousPalette2() {
		return previousPalette2;
	}

	public void setPreviousPalette2(int previousPalette2) {
		this.previousPalette2 = previousPalette2;
	}

	public int getPreviousPalette3() {
		return previousPalette3;
	}

	public void setPreviousPalette3(int previousPalette3) {
		this.previousPalette3 = previousPalette3;
	}

	public int getPreviousPalette4() {
		return previousPalette4;
	}

	public void setPreviousPalette4(int previousPalette4) {
		this.previousPalette4 = previousPalette4;
	}

	public float getVacuumSuctionSpeed() {
		return vacuumSuctionSpeed;
	}

	public void setVacuumSuctionSpeed(float vacuumSuctionSpeed) {
		this.vacuumSuctionSpeed = vacuumSuctionSpeed;
	}

	public long getBurstModeCooldown() {
		return burstModeCooldown;
	}

	public void setBurstModeCooldown(long burstModeCooldown) {
		this.burstModeCooldown = burstModeCooldown;
	}

	public long getBurstModeDur() {
		return burstModeDur;
	}

	public void setBurstModeDur(long burstModeDur) {
		this.burstModeDur = burstModeDur;
	}

	public float getTrashBlowerBlowSpeed() {
		return trashBlowerBlowSpeed;
	}

	public void setTrashBlowerBlowSpeed(float trashBlowerBlowSpeed) {
		this.trashBlowerBlowSpeed = trashBlowerBlowSpeed;
	}

	public float getSwiftCanDegen() {
		return swiftCanDegen;
	}

	public void setSwiftCanDegen(float swiftCanDegen) {
		this.swiftCanDegen = swiftCanDegen;
	}

	public float getSwiftCanEnergyTot() {
		return swiftCanEnergyTot;
	}

	public void setSwiftCanEnergyTot(float swiftCanEnergyTot) {
		this.swiftCanEnergyTot = swiftCanEnergyTot;
	}

	public boolean isSwitchMachine() {
		return switchMachine;
	}

	public void setSwitchMachine(boolean switchMachine) {
		this.switchMachine = switchMachine;
	}

	public boolean isAugmentedBurst() {
		return augmentedBurst;
	}

	public void setAugmentedBurst(boolean augmentedBurst) {
		this.augmentedBurst = augmentedBurst;
	}

	public boolean isSticker() {
		return sticker;
	}

	public void setSticker(boolean sticker) {
		this.sticker = sticker;
	}

	public boolean isLessThanReturningXPos() {
		return lessThanReturningXPos;
	}

	public void setLessThanReturningXPos(boolean lessThanReturningXPos) {
		this.lessThanReturningXPos = lessThanReturningXPos;
	}

	public int getAverageCanCapacity() {
		return averageCanCapacity;
	}

	public void setAverageCanCapacity(int averageCanCapacity) {
		this.averageCanCapacity = averageCanCapacity;
	}

	public int getScorchingCanCapacity() {
		return scorchingCanCapacity;
	}

	public void setScorchingCanCapacity(int scorchingCanCapacity) {
		this.scorchingCanCapacity = scorchingCanCapacity;
	}

	public int getVacuumCanCapacity() {
		return vacuumCanCapacity;
	}

	public void setVacuumCanCapacity(int vacuumCanCapacity) {
		this.vacuumCanCapacity = vacuumCanCapacity;
	}

	public int getTrashBlowerCapacity() {
		return trashBlowerCapacity;
	}

	public void setTrashBlowerCapacity(int trashBlowerCapacity) {
		this.trashBlowerCapacity = trashBlowerCapacity;
	}

	public int getDullCanCapacity() {
		return dullCanCapacity;
	}

	public void setDullCanCapacity(int dullCanCapacity) {
		this.dullCanCapacity = dullCanCapacity;
	}

	public int getSwiftCanCapacity() {
		return swiftCanCapacity;
	}

	public void setSwiftCanCapacity(int swiftCanCapacity) {
		this.swiftCanCapacity = swiftCanCapacity;
	}

	public boolean isHouseUnlocked() {
		return houseUnlocked;
	}

	public void setHouseUnlocked(boolean houseUnlocked) {
		this.houseUnlocked = houseUnlocked;
	}

	public boolean isCityUnlocked() {
		return cityUnlocked;
	}

	public void setCityUnlocked(boolean cityUnlocked) {
		this.cityUnlocked = cityUnlocked;
	}

	public boolean isForestUnlocked() {
		return forestUnlocked;
	}

	public void setForestUnlocked(boolean forestUnlocked) {
		this.forestUnlocked = forestUnlocked;
	}

	public boolean isKitchenUnlocked() {
		return kitchenUnlocked;
	}

	public void setKitchenUnlocked(boolean kitchenUnlocked) {
		this.kitchenUnlocked = kitchenUnlocked;
	}

	public boolean isBasementUnlocked() {
		return basementUnlocked;
	}

	public void setBasementUnlocked(boolean basementUnlocked) {
		this.basementUnlocked = basementUnlocked;
	}

	public boolean isGarageUnlocked() {
		return garageUnlocked;
	}

	public void setGarageUnlocked(boolean garageUnlocked) {
		this.garageUnlocked = garageUnlocked;
	}

	public boolean isBackyardUnlocked() {
		return backyardUnlocked;
	}

	public void setBackyardUnlocked(boolean backyardUnlocked) {
		this.backyardUnlocked = backyardUnlocked;
	}

	public boolean isSidewalkUnlocked() {
		return sidewalkUnlocked;
	}

	public void setSidewalkUnlocked(boolean sidewalkUnlocked) {
		this.sidewalkUnlocked = sidewalkUnlocked;
	}

	public boolean isFactoryUnlocked() {
		return factoryUnlocked;
	}

	public void setFactoryUnlocked(boolean factoryUnlocked) {
		this.factoryUnlocked = factoryUnlocked;
	}

	public boolean isHighwayUnlocked() {
		return highwayUnlocked;
	}

	public void setHighwayUnlocked(boolean highwayUnlocked) {
		this.highwayUnlocked = highwayUnlocked;
	}

	public boolean isParkUnlocked() {
		return parkUnlocked;
	}

	public void setParkUnlocked(boolean parkUnlocked) {
		this.parkUnlocked = parkUnlocked;
	}

	public boolean isSewerUnlocked() {
		return sewerUnlocked;
	}

	public void setSewerUnlocked(boolean sewerUnlocked) {
		this.sewerUnlocked = sewerUnlocked;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getJunkCoins() {
		return junkCoins;
	}

	public void setJunkCoins(int junkCoins) {
		this.junkCoins = junkCoins;
	}

	public boolean isKitchen1Unlocked() {
		return kitchen1Unlocked;
	}

	public void setKitchen1Unlocked(boolean kitchen1Unlocked) {
		this.kitchen1Unlocked = kitchen1Unlocked;
	}

	public boolean isKitchen2Unlocked() {
		return kitchen2Unlocked;
	}

	public void setKitchen2Unlocked(boolean kitchen2Unlocked) {
		this.kitchen2Unlocked = kitchen2Unlocked;
	}

	public boolean isKitchen3Unlocked() {
		return kitchen3Unlocked;
	}

	public void setKitchen3Unlocked(boolean kitchen3Unlocked) {
		this.kitchen3Unlocked = kitchen3Unlocked;
	}

	public boolean isKitchen4Unlocked() {
		return kitchen4Unlocked;
	}

	public void setKitchen4Unlocked(boolean kitchen4Unlocked) {
		this.kitchen4Unlocked = kitchen4Unlocked;
	}

	public boolean isKitchen5Unlocked() {
		return kitchen5Unlocked;
	}

	public void setKitchen5Unlocked(boolean kitchen5Unlocked) {
		this.kitchen5Unlocked = kitchen5Unlocked;
	}

	public boolean isBasement1Unlocked() {
		return basement1Unlocked;
	}

	public void setBasement1Unlocked(boolean basement1Unlocked) {
		this.basement1Unlocked = basement1Unlocked;
	}

	public boolean isBasement2Unlocked() {
		return basement2Unlocked;
	}

	public void setBasement2Unlocked(boolean basement2Unlocked) {
		this.basement2Unlocked = basement2Unlocked;
	}

	public boolean isBasement3Unlocked() {
		return basement3Unlocked;
	}

	public void setBasement3Unlocked(boolean basement3Unlocked) {
		this.basement3Unlocked = basement3Unlocked;
	}

	public boolean isBasement4Unlocked() {
		return basement4Unlocked;
	}

	public void setBasement4Unlocked(boolean basement4Unlocked) {
		this.basement4Unlocked = basement4Unlocked;
	}

	public boolean isBasement5Unlocked() {
		return basement5Unlocked;
	}

	public void setBasement5Unlocked(boolean basement5Unlocked) {
		this.basement5Unlocked = basement5Unlocked;
	}

	public boolean isGarage1Unlocked() {
		return garage1Unlocked;
	}

	public void setGarage1Unlocked(boolean garage1Unlocked) {
		this.garage1Unlocked = garage1Unlocked;
	}

	public boolean isGarage2Unlocked() {
		return garage2Unlocked;
	}

	public void setGarage2Unlocked(boolean garage2Unlocked) {
		this.garage2Unlocked = garage2Unlocked;
	}

	public boolean isGarage3Unlocked() {
		return garage3Unlocked;
	}

	public void setGarage3Unlocked(boolean garage3Unlocked) {
		this.garage3Unlocked = garage3Unlocked;
	}

	public boolean isGarage4Unlocked() {
		return garage4Unlocked;
	}

	public void setGarage4Unlocked(boolean garage4Unlocked) {
		this.garage4Unlocked = garage4Unlocked;
	}

	public boolean isGarage5Unlocked() {
		return garage5Unlocked;
	}

	public void setGarage5Unlocked(boolean garage5Unlocked) {
		this.garage5Unlocked = garage5Unlocked;
	}

	public boolean isBackyard1Unlocked() {
		return backyard1Unlocked;
	}

	public void setBackyard1Unlocked(boolean backyard1Unlocked) {
		this.backyard1Unlocked = backyard1Unlocked;
	}

	public boolean isBackyard2Unlocked() {
		return backyard2Unlocked;
	}

	public void setBackyard2Unlocked(boolean backyard2Unlocked) {
		this.backyard2Unlocked = backyard2Unlocked;
	}

	public boolean isBackyard3Unlocked() {
		return backyard3Unlocked;
	}

	public void setBackyard3Unlocked(boolean backyard3Unlocked) {
		this.backyard3Unlocked = backyard3Unlocked;
	}

	public boolean isBackyard4Unlocked() {
		return backyard4Unlocked;
	}

	public void setBackyard4Unlocked(boolean backyard4Unlocked) {
		this.backyard4Unlocked = backyard4Unlocked;
	}

	public boolean isBackyard5Unlocked() {
		return backyard5Unlocked;
	}

	public void setBackyard5Unlocked(boolean backyard5Unlocked) {
		this.backyard5Unlocked = backyard5Unlocked;
	}

	public boolean isSidewalk1Unlocked() {
		return sidewalk1Unlocked;
	}

	public void setSidewalk1Unlocked(boolean sidewalk1Unlocked) {
		this.sidewalk1Unlocked = sidewalk1Unlocked;
	}

	public boolean isSidewalk2Unlocked() {
		return sidewalk2Unlocked;
	}

	public void setSidewalk2Unlocked(boolean sidewalk2Unlocked) {
		this.sidewalk2Unlocked = sidewalk2Unlocked;
	}

	public boolean isSidewalk3Unlocked() {
		return sidewalk3Unlocked;
	}

	public void setSidewalk3Unlocked(boolean sidewalk3Unlocked) {
		this.sidewalk3Unlocked = sidewalk3Unlocked;
	}

	public boolean isSidewalk4Unlocked() {
		return sidewalk4Unlocked;
	}

	public void setSidewalk4Unlocked(boolean sidewalk4Unlocked) {
		this.sidewalk4Unlocked = sidewalk4Unlocked;
	}

	public boolean isSidewalk5Unlocked() {
		return sidewalk5Unlocked;
	}

	public void setSidewalk5Unlocked(boolean sidewalk5Unlocked) {
		this.sidewalk5Unlocked = sidewalk5Unlocked;
	}

	public boolean isFactory1Unlocked() {
		return factory1Unlocked;
	}

	public void setFactory1Unlocked(boolean factory1Unlocked) {
		this.factory1Unlocked = factory1Unlocked;
	}

	public boolean isFactory2Unlocked() {
		return factory2Unlocked;
	}

	public void setFactory2Unlocked(boolean factory2Unlocked) {
		this.factory2Unlocked = factory2Unlocked;
	}

	public boolean isFactory3Unlocked() {
		return factory3Unlocked;
	}

	public void setFactory3Unlocked(boolean factory3Unlocked) {
		this.factory3Unlocked = factory3Unlocked;
	}

	public boolean isFactory4Unlocked() {
		return factory4Unlocked;
	}

	public void setFactory4Unlocked(boolean factory4Unlocked) {
		this.factory4Unlocked = factory4Unlocked;
	}

	public boolean isFactory5Unlocked() {
		return factory5Unlocked;
	}

	public void setFactory5Unlocked(boolean factory5Unlocked) {
		this.factory5Unlocked = factory5Unlocked;
	}

	public boolean isHighway1Unlocked() {
		return highway1Unlocked;
	}

	public void setHighway1Unlocked(boolean highway1Unlocked) {
		this.highway1Unlocked = highway1Unlocked;
	}

	public boolean isHighway2Unlocked() {
		return highway2Unlocked;
	}

	public void setHighway2Unlocked(boolean highway2Unlocked) {
		this.highway2Unlocked = highway2Unlocked;
	}

	public boolean isHighway3Unlocked() {
		return highway3Unlocked;
	}

	public void setHighway3Unlocked(boolean highway3Unlocked) {
		this.highway3Unlocked = highway3Unlocked;
	}

	public boolean isHighway4Unlocked() {
		return highway4Unlocked;
	}

	public void setHighway4Unlocked(boolean highway4Unlocked) {
		this.highway4Unlocked = highway4Unlocked;
	}

	public boolean isHighway5Unlocked() {
		return highway5Unlocked;
	}

	public void setHighway5Unlocked(boolean highway5Unlocked) {
		this.highway5Unlocked = highway5Unlocked;
	}

	public boolean isPark1Unlocked() {
		return park1Unlocked;
	}

	public void setPark1Unlocked(boolean park1Unlocked) {
		this.park1Unlocked = park1Unlocked;
	}

	public boolean isPark2Unlocked() {
		return park2Unlocked;
	}

	public void setPark2Unlocked(boolean park2Unlocked) {
		this.park2Unlocked = park2Unlocked;
	}

	public boolean isPark3Unlocked() {
		return park3Unlocked;
	}

	public void setPark3Unlocked(boolean park3Unlocked) {
		this.park3Unlocked = park3Unlocked;
	}

	public boolean isPark4Unlocked() {
		return park4Unlocked;
	}

	public void setPark4Unlocked(boolean park4Unlocked) {
		this.park4Unlocked = park4Unlocked;
	}

	public boolean isPark5Unlocked() {
		return park5Unlocked;
	}

	public void setPark5Unlocked(boolean park5Unlocked) {
		this.park5Unlocked = park5Unlocked;
	}

	public boolean isSewer1Unlocked() {
		return sewer1Unlocked;
	}

	public void setSewer1Unlocked(boolean sewer1Unlocked) {
		this.sewer1Unlocked = sewer1Unlocked;
	}

	public boolean isSewer2Unlocked() {
		return sewer2Unlocked;
	}

	public void setSewer2Unlocked(boolean sewer2Unlocked) {
		this.sewer2Unlocked = sewer2Unlocked;
	}

	public boolean isSewer3Unlocked() {
		return sewer3Unlocked;
	}

	public void setSewer3Unlocked(boolean sewer3Unlocked) {
		this.sewer3Unlocked = sewer3Unlocked;
	}

	public boolean isSewer4Unlocked() {
		return sewer4Unlocked;
	}

	public void setSewer4Unlocked(boolean sewer4Unlocked) {
		this.sewer4Unlocked = sewer4Unlocked;
	}

	public boolean isSewer5Unlocked() {
		return sewer5Unlocked;
	}

	public void setSewer5Unlocked(boolean sewer5Unlocked) {
		this.sewer5Unlocked = sewer5Unlocked;
	}

	public String getCurrentJob() {
		return currentJob;
	}

	public void setCurrentJob(String currentJob) {
		this.currentJob = currentJob;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public float getCurrentXP() {
		return currentXP;
	}

	public void setCurrentXP(float currentXP) {
		this.currentXP = currentXP;
	}

	public float getXpLevelUp() {
		return xpLevelUp;
	}

	public void setXpLevelUp(float xpLevelUp) {
		this.xpLevelUp = xpLevelUp;
	}

	public boolean isRed() {
		return red;
	}

	public void setRed(boolean red) {
		this.red = red;
	}

	public boolean isOrange() {
		return orange;
	}

	public void setOrange(boolean orange) {
		this.orange = orange;
	}

	public boolean isYellow() {
		return yellow;
	}

	public void setYellow(boolean yellow) {
		this.yellow = yellow;
	}

	public boolean isGreen() {
		return green;
	}

	public void setGreen(boolean green) {
		this.green = green;
	}

	public boolean isBlue() {
		return blue;
	}

	public void setBlue(boolean blue) {
		this.blue = blue;
	}

	public boolean isPurple() {
		return purple;
	}

	public void setPurple(boolean purple) {
		this.purple = purple;
	}

	public boolean isBiodegradable() {
		return biodegradable;
	}

	public void setBiodegradable(boolean biodegradable) {
		this.biodegradable = biodegradable;
	}

	public boolean isRecyclable() {
		return recyclable;
	}

	public void setRecyclable(boolean recyclable) {
		this.recyclable = recyclable;
	}

	public boolean isNonRecyclable() {
		return nonRecyclable;
	}

	public void setNonRecyclable(boolean nonRecyclable) {
		this.nonRecyclable = nonRecyclable;
	}

	public boolean isBlowerSkill1Unlocked() {
		return blowerSkill1Unlocked;
	}

	public void setBlowerSkill1Unlocked(boolean blowerSkill1Unlocked) {
		this.blowerSkill1Unlocked = blowerSkill1Unlocked;
	}

	public boolean isBlowerSkill2Unlocked() {
		return blowerSkill2Unlocked;
	}

	public void setBlowerSkill2Unlocked(boolean blowerSkill2Unlocked) {
		this.blowerSkill2Unlocked = blowerSkill2Unlocked;
	}

	public boolean isBlowerSkill3Unlocked() {
		return blowerSkill3Unlocked;
	}

	public void setBlowerSkill3Unlocked(boolean blowerSkill3Unlocked) {
		this.blowerSkill3Unlocked = blowerSkill3Unlocked;
	}

	public boolean isBlowerSkill4Unlocked() {
		return blowerSkill4Unlocked;
	}

	public void setBlowerSkill4Unlocked(boolean blowerSkill4Unlocked) {
		this.blowerSkill4Unlocked = blowerSkill4Unlocked;
	}

	public boolean isDullSkill1Unlocked() {
		return dullSkill1Unlocked;
	}

	public void setDullSkill1Unlocked(boolean dullSkill1Unlocked) {
		this.dullSkill1Unlocked = dullSkill1Unlocked;
	}

	public boolean isDullSkill2Unlocked() {
		return dullSkill2Unlocked;
	}

	public void setDullSkill2Unlocked(boolean dullSkill2Unlocked) {
		this.dullSkill2Unlocked = dullSkill2Unlocked;
	}

	public boolean isDullSkill3Unlocked() {
		return dullSkill3Unlocked;
	}

	public void setDullSkill3Unlocked(boolean dullSkill3Unlocked) {
		this.dullSkill3Unlocked = dullSkill3Unlocked;
	}

	public boolean isDullSkill4Unlocked() {
		return dullSkill4Unlocked;
	}

	public void setDullSkill4Unlocked(boolean dullSkill4Unlocked) {
		this.dullSkill4Unlocked = dullSkill4Unlocked;
	}

	public boolean isSwiftSkill1Unlocked() {
		return swiftSkill1Unlocked;
	}

	public void setSwiftSkill1Unlocked(boolean swiftSkill1Unlocked) {
		this.swiftSkill1Unlocked = swiftSkill1Unlocked;
	}

	public boolean isSwiftSkill2Unlocked() {
		return swiftSkill2Unlocked;
	}

	public void setSwiftSkill2Unlocked(boolean swiftSkill2Unlocked) {
		this.swiftSkill2Unlocked = swiftSkill2Unlocked;
	}

	public boolean isSwiftSkill3Unlocked() {
		return swiftSkill3Unlocked;
	}

	public void setSwiftSkill3Unlocked(boolean swiftSkill3Unlocked) {
		this.swiftSkill3Unlocked = swiftSkill3Unlocked;
	}

	public boolean isSwiftSkill4Unlocked() {
		return swiftSkill4Unlocked;
	}

	public void setSwiftSkill4Unlocked(boolean swiftSkill4Unlocked) {
		this.swiftSkill4Unlocked = swiftSkill4Unlocked;
	}

	public int getTheme() {
		return theme;
	}

	public void setTheme(int theme) {
		this.theme = theme;
	}

	public boolean isAcornAlbum() {
		return acornAlbum;
	}

	public void setAcornAlbum(boolean acornAlbum) {
		this.acornAlbum = acornAlbum;
	}

	public boolean isAppleAlbum() {
		return appleAlbum;
	}

	public void setAppleAlbum(boolean appleAlbum) {
		this.appleAlbum = appleAlbum;
	}

	public boolean isBananaPeelAlbum() {
		return bananaPeelAlbum;
	}

	public void setBananaPeelAlbum(boolean bananaPeelAlbum) {
		this.bananaPeelAlbum = bananaPeelAlbum;
	}

	public boolean isBranchAlbum() {
		return branchAlbum;
	}

	public void setBranchAlbum(boolean branchAlbum) {
		this.branchAlbum = branchAlbum;
	}

	public boolean isDeadMouseAlbum() {
		return deadMouseAlbum;
	}

	public void setDeadMouseAlbum(boolean deadMouseAlbum) {
		this.deadMouseAlbum = deadMouseAlbum;
	}

	public boolean isEggShellAlbum() {
		return eggShellAlbum;
	}

	public void setEggShellAlbum(boolean eggShellAlbum) {
		this.eggShellAlbum = eggShellAlbum;
	}

	public boolean isFeatherAlbum() {
		return featherAlbum;
	}

	public void setFeatherAlbum(boolean featherAlbum) {
		this.featherAlbum = featherAlbum;
	}

	public boolean isFishBoneAlbum() {
		return fishBoneAlbum;
	}

	public void setFishBoneAlbum(boolean fishBoneAlbum) {
		this.fishBoneAlbum = fishBoneAlbum;
	}

	public boolean isFlowerAlbum() {
		return flowerAlbum;
	}

	public void setFlowerAlbum(boolean flowerAlbum) {
		this.flowerAlbum = flowerAlbum;
	}

	public boolean isGrassAlbum() {
		return grassAlbum;
	}

	public void setGrassAlbum(boolean grassAlbum) {
		this.grassAlbum = grassAlbum;
	}

	public boolean isHairAlbum() {
		return hairAlbum;
	}

	public void setHairAlbum(boolean hairAlbum) {
		this.hairAlbum = hairAlbum;
	}

	public boolean isHayAlbum() {
		return hayAlbum;
	}

	public void setHayAlbum(boolean hayAlbum) {
		this.hayAlbum = hayAlbum;
	}

	public boolean isLeavesAlbum() {
		return leavesAlbum;
	}

	public void setLeavesAlbum(boolean leavesAlbum) {
		this.leavesAlbum = leavesAlbum;
	}

	public boolean isManureAlbum() {
		return manureAlbum;
	}

	public void setManureAlbum(boolean manureAlbum) {
		this.manureAlbum = manureAlbum;
	}

	public boolean isRootsAlbum() {
		return rootsAlbum;
	}

	public void setRootsAlbum(boolean rootsAlbum) {
		this.rootsAlbum = rootsAlbum;
	}

	public boolean isBottleAlbum() {
		return bottleAlbum;
	}

	public void setBottleAlbum(boolean bottleAlbum) {
		this.bottleAlbum = bottleAlbum;
	}

	public boolean isCanAlbum() {
		return canAlbum;
	}

	public void setCanAlbum(boolean canAlbum) {
		this.canAlbum = canAlbum;
	}

	public boolean isCardboardAlbum() {
		return cardboardAlbum;
	}

	public void setCardboardAlbum(boolean cardboardAlbum) {
		this.cardboardAlbum = cardboardAlbum;
	}

	public boolean isCerealBoxAlbum() {
		return cerealBoxAlbum;
	}

	public void setCerealBoxAlbum(boolean cerealBoxAlbum) {
		this.cerealBoxAlbum = cerealBoxAlbum;
	}

	public boolean isDirtyShirtAlbum() {
		return dirtyShirtAlbum;
	}

	public void setDirtyShirtAlbum(boolean dirtyShirtAlbum) {
		this.dirtyShirtAlbum = dirtyShirtAlbum;
	}

	public boolean isEnvelopeAlbum() {
		return envelopeAlbum;
	}

	public void setEnvelopeAlbum(boolean envelopeAlbum) {
		this.envelopeAlbum = envelopeAlbum;
	}

	public boolean isFunnelAlbum() {
		return funnelAlbum;
	}

	public void setFunnelAlbum(boolean funnelAlbum) {
		this.funnelAlbum = funnelAlbum;
	}

	public boolean isHangerAlbum() {
		return hangerAlbum;
	}

	public void setHangerAlbum(boolean hangerAlbum) {
		this.hangerAlbum = hangerAlbum;
	}

	public boolean isNewspaperAlbum() {
		return newspaperAlbum;
	}

	public void setNewspaperAlbum(boolean newspaperAlbum) {
		this.newspaperAlbum = newspaperAlbum;
	}

	public boolean isPaperAlbum() {
		return paperAlbum;
	}

	public void setPaperAlbum(boolean paperAlbum) {
		this.paperAlbum = paperAlbum;
	}

	public boolean isPillBottleAlbum() {
		return pillBottleAlbum;
	}

	public void setPillBottleAlbum(boolean pillBottleAlbum) {
		this.pillBottleAlbum = pillBottleAlbum;
	}

	public boolean isPlasticBagAlbum() {
		return plasticBagAlbum;
	}

	public void setPlasticBagAlbum(boolean plasticBagAlbum) {
		this.plasticBagAlbum = plasticBagAlbum;
	}

	public boolean isTyreAlbum() {
		return tyreAlbum;
	}

	public void setTyreAlbum(boolean tyreAlbum) {
		this.tyreAlbum = tyreAlbum;
	}

	public boolean isVaseAlbum() {
		return vaseAlbum;
	}

	public void setVaseAlbum(boolean vaseAlbum) {
		this.vaseAlbum = vaseAlbum;
	}

	public boolean isWaffleIronAlbum() {
		return waffleIronAlbum;
	}

	public void setWaffleIronAlbum(boolean waffleIronAlbum) {
		this.waffleIronAlbum = waffleIronAlbum;
	}

	public boolean isAerosolCanAlbum() {
		return aerosolCanAlbum;
	}

	public void setAerosolCanAlbum(boolean aerosolCanAlbum) {
		this.aerosolCanAlbum = aerosolCanAlbum;
	}

	public boolean isBrokenBulbAlbum() {
		return brokenBulbAlbum;
	}

	public void setBrokenBulbAlbum(boolean brokenBulbAlbum) {
		this.brokenBulbAlbum = brokenBulbAlbum;
	}

	public boolean isBrokenGlassAlbum() {
		return brokenGlassAlbum;
	}

	public void setBrokenGlassAlbum(boolean brokenGlassAlbum) {
		this.brokenGlassAlbum = brokenGlassAlbum;
	}

	public boolean isChewingGumAlbum() {
		return chewingGumAlbum;
	}

	public void setChewingGumAlbum(boolean chewingGumAlbum) {
		this.chewingGumAlbum = chewingGumAlbum;
	}

	public boolean isCigaretteAlbum() {
		return cigaretteAlbum;
	}

	public void setCigaretteAlbum(boolean cigaretteAlbum) {
		this.cigaretteAlbum = cigaretteAlbum;
	}

	public boolean isDeadBatteryAlbum() {
		return deadBatteryAlbum;
	}

	public void setDeadBatteryAlbum(boolean deadBatteryAlbum) {
		this.deadBatteryAlbum = deadBatteryAlbum;
	}

	public boolean isDirtyDiaperAlbum() {
		return dirtyDiaperAlbum;
	}

	public void setDirtyDiaperAlbum(boolean dirtyDiaperAlbum) {
		this.dirtyDiaperAlbum = dirtyDiaperAlbum;
	}

	public boolean isInsecticideSprayAlbum() {
		return insecticideSprayAlbum;
	}

	public void setInsecticideSprayAlbum(boolean insecticideSprayAlbum) {
		this.insecticideSprayAlbum = insecticideSprayAlbum;
	}

	public boolean isLeftoverCakeAlbum() {
		return leftoverCakeAlbum;
	}

	public void setLeftoverCakeAlbum(boolean leftoverCakeAlbum) {
		this.leftoverCakeAlbum = leftoverCakeAlbum;
	}

	public boolean isLeftoverChickenAlbum() {
		return leftoverChickenAlbum;
	}

	public void setLeftoverChickenAlbum(boolean leftoverChickenAlbum) {
		this.leftoverChickenAlbum = leftoverChickenAlbum;
	}

	public boolean isNailAlbum() {
		return nailAlbum;
	}

	public void setNailAlbum(boolean nailAlbum) {
		this.nailAlbum = nailAlbum;
	}

	public boolean isPaintCanAlbum() {
		return paintCanAlbum;
	}

	public void setPaintCanAlbum(boolean paintCanAlbum) {
		this.paintCanAlbum = paintCanAlbum;
	}

	public boolean isSyringeAlbum() {
		return syringeAlbum;
	}

	public void setSyringeAlbum(boolean syringeAlbum) {
		this.syringeAlbum = syringeAlbum;
	}

	public boolean isTornPaperAlbum() {
		return tornPaperAlbum;
	}

	public void setTornPaperAlbum(boolean tornPaperAlbum) {
		this.tornPaperAlbum = tornPaperAlbum;
	}

	public boolean isUsedMotorOilAlbum() {
		return usedMotorOilAlbum;
	}

	public void setUsedMotorOilAlbum(boolean usedMotorOilAlbum) {
		this.usedMotorOilAlbum = usedMotorOilAlbum;
	}

	public boolean isAverageCanUnlocked() {
		return averageCanUnlocked;
	}

	public void setAverageCanUnlocked(boolean averageCanUnlocked) {
		this.averageCanUnlocked = averageCanUnlocked;
	}

	public boolean isScorchingCanUnlocked() {
		return scorchingCanUnlocked;
	}

	public void setScorchingCanUnlocked(boolean scorchingCanUnlocked) {
		this.scorchingCanUnlocked = scorchingCanUnlocked;
	}

	public boolean isVacuumCanUnlocked() {
		return vacuumCanUnlocked;
	}

	public void setVacuumCanUnlocked(boolean vacuumCanUnlocked) {
		this.vacuumCanUnlocked = vacuumCanUnlocked;
	}

	public boolean isTrashBlowerUnlocked() {
		return trashBlowerUnlocked;
	}

	public void setTrashBlowerUnlocked(boolean trashBlowerUnlocked) {
		this.trashBlowerUnlocked = trashBlowerUnlocked;
	}

	public boolean isDullCanUnlocked() {
		return dullCanUnlocked;
	}

	public void setDullCanUnlocked(boolean dullCanUnlocked) {
		this.dullCanUnlocked = dullCanUnlocked;
	}

	public boolean isSwiftCanUnlocked() {
		return swiftCanUnlocked;
	}

	public void setSwiftCanUnlocked(boolean swiftCanUnlocked) {
		this.swiftCanUnlocked = swiftCanUnlocked;
	}

	public boolean isTrashSpawnRow2() {
		return trashSpawnRow2;
	}

	public void setTrashSpawnRow2(boolean trashSpawnRow2) {
		this.trashSpawnRow2 = trashSpawnRow2;
	}

	public boolean isTrashSpawnRow3() {
		return trashSpawnRow3;
	}

	public void setTrashSpawnRow3(boolean trashSpawnRow3) {
		this.trashSpawnRow3 = trashSpawnRow3;
	}

	public boolean isTrashSpawnRow4() {
		return trashSpawnRow4;
	}

	public void setTrashSpawnRow4(boolean trashSpawnRow4) {
		this.trashSpawnRow4 = trashSpawnRow4;
	}

	public int getHighestScoreDay() {
		return highestScoreDay;
	}

	public void setHighestScoreDay(int highestScoreDay) {
		this.highestScoreDay = highestScoreDay;
	}

	public int getHighestScoreEver() {
		return highestScoreEver;
	}

	public void setHighestScoreEver(int highestScoreEver) {
		this.highestScoreEver = highestScoreEver;
	}

	public Array<Float> getSpawnTime() {
		return spawnTime;
	}

	public void setSpawnTime(Array<Float> spawnTime) {
		this.spawnTime = spawnTime;
	}

	public int getGameMode() {
		return gameMode;
	}

	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}

	public int getGarbageGoal() {
		return garbageGoal;
	}

	public void setGarbageGoal(int garbageGoal) {
		this.garbageGoal = garbageGoal;
	}

	public int getGameSelection() {
		return gameSelection;
	}

	public void setGameSelection(int gameSelection) {
		this.gameSelection = gameSelection;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public Array<Texture> getTutorial() {
		return tutorial;
	}

	public void setTutorial(Array<Texture> tutorial) {
		this.tutorial = tutorial;
	}

	public Array<Texture> getTutorialArrow() {
		return tutorialArrow;
	}

	public void setTutorialArrow(Array<Texture> tutorialArrow) {
		this.tutorialArrow = tutorialArrow;
	}

	public Rectangle getTutorialLayer() {
		return tutorialLayer;
	}

	public void setTutorialLayer(Rectangle tutorialLayer) {
		this.tutorialLayer = tutorialLayer;
	}

	public Stage getTutorialStage() {
		return tutorialStage;
	}

	public void setTutorialStage(Stage tutorialStage) {
		this.tutorialStage = tutorialStage;
	}

	public Actor getQuitTutorial() {
		return quitTutorial;
	}

	public void setQuitTutorial(Actor quitTutorial) {
		this.quitTutorial = quitTutorial;
	}

	public int getCurrentTutorial() {
		return currentTutorial;
	}

	public void setCurrentTutorial(int currentTutorial) {
		this.currentTutorial = currentTutorial;
	}

	public Actor getClickContinueTutorial() {
		return clickContinueTutorial;
	}

	public void setClickContinueTutorial(Actor clickContinueTutorial) {
		this.clickContinueTutorial = clickContinueTutorial;
	}

	public Sprite getCurrentArrow() {
		return currentArrow;
	}

	public void setCurrentArrow(Sprite currentArrow) {
		this.currentArrow = currentArrow;
	}

	public long getDelayArrow() {
		return delayArrow;
	}

	public void setDelayArrow(long delayArrow) {
		this.delayArrow = delayArrow;
	}

	public int getCurrentArrowNum() {
		return currentArrowNum;
	}

	public void setCurrentArrowNum(int currentArrowNum) {
		this.currentArrowNum = currentArrowNum;
	}

	public int getGarbageAmount() {
		return garbageAmount;
	}

	public void setGarbageAmount(int garbageAmount) {
		this.garbageAmount = garbageAmount;
	}

	public int getPreviousTrashCan1Color() {
		return previousTrashCan1Color;
	}

	public void setPreviousTrashCan1Color(int previousTrashCan1Color) {
		this.previousTrashCan1Color = previousTrashCan1Color;
	}

	public int getPreviousTrashCan1Type() {
		return previousTrashCan1Type;
	}

	public void setPreviousTrashCan1Type(int previousTrashCan1Type) {
		this.previousTrashCan1Type = previousTrashCan1Type;
	}

	public int getPreviousTrashCan2Color() {
		return previousTrashCan2Color;
	}

	public void setPreviousTrashCan2Color(int previousTrashCan2Color) {
		this.previousTrashCan2Color = previousTrashCan2Color;
	}

	public int getPreviousTrashCan2Type() {
		return previousTrashCan2Type;
	}

	public void setPreviousTrashCan2Type(int previousTrashCan2Type) {
		this.previousTrashCan2Type = previousTrashCan2Type;
	}

	public int getPreviousTrashCan3Color() {
		return previousTrashCan3Color;
	}

	public void setPreviousTrashCan3Color(int previousTrashCan3Color) {
		this.previousTrashCan3Color = previousTrashCan3Color;
	}

	public int getPreviousTrashCan3Type() {
		return previousTrashCan3Type;
	}

	public void setPreviousTrashCan3Type(int previousTrashCan3Type) {
		this.previousTrashCan3Type = previousTrashCan3Type;
	}

	public int getPreviousTrashCan4Color() {
		return previousTrashCan4Color;
	}

	public void setPreviousTrashCan4Color(int previousTrashCan4Color) {
		this.previousTrashCan4Color = previousTrashCan4Color;
	}

	public int getPreviousTrashCan4Type() {
		return previousTrashCan4Type;
	}

	public void setPreviousTrashCan4Type(int previousTrashCan4Type) {
		this.previousTrashCan4Type = previousTrashCan4Type;
	}

	public int getDullPrevColor1() {
		return dullPrevColor1;
	}

	public void setDullPrevColor1(int dullPrevColor1) {
		this.dullPrevColor1 = dullPrevColor1;
	}

	public int getDullPrevColor2() {
		return dullPrevColor2;
	}

	public void setDullPrevColor2(int dullPrevColor2) {
		this.dullPrevColor2 = dullPrevColor2;
	}

	public int getDullPrevColor3() {
		return dullPrevColor3;
	}

	public void setDullPrevColor3(int dullPrevColor3) {
		this.dullPrevColor3 = dullPrevColor3;
	}

	public int getDullPrevColor4() {
		return dullPrevColor4;
	}

	public void setDullPrevColor4(int dullPrevColor4) {
		this.dullPrevColor4 = dullPrevColor4;
	}

	public TrashCan1Properties getTrashCan1Properties() {
		return trashCan1Properties;
	}

	public void setTrashCan1Properties(TrashCan1Properties trashCan1Properties) {
		this.trashCan1Properties = trashCan1Properties;
	}

	public TrashCan2Properties getTrashCan2Properties() {
		return trashCan2Properties;
	}

	public void setTrashCan2Properties(TrashCan2Properties trashCan2Properties) {
		this.trashCan2Properties = trashCan2Properties;
	}

	public TrashCan3Properties getTrashCan3Properties() {
		return trashCan3Properties;
	}

	public void setTrashCan3Properties(TrashCan3Properties trashCan3Properties) {
		this.trashCan3Properties = trashCan3Properties;
	}

	public TrashCan4Properties getTrashCan4Properties() {
		return trashCan4Properties;
	}

	public void setTrashCan4Properties(TrashCan4Properties trashCan4Properties) {
		this.trashCan4Properties = trashCan4Properties;
	}

	public GamePalette getGamePalette() {
		return gamePalette;
	}

	public void setGamePalette(GamePalette gamePalette) {
		this.gamePalette = gamePalette;
	}

	public int getCurrentBackgroundMusic() {
		return currentBackgroundMusic;
	}

	public void setCurrentBackgroundMusic(int currentBackgroundMusic) {
		this.currentBackgroundMusic = currentBackgroundMusic;
	}

	public int getCurrentDifficulty() {
		return currentDifficulty;
	}

	public void setCurrentDifficulty(int currentDifficulty) {
		this.currentDifficulty = currentDifficulty;
	}
	
//	TODO
}
