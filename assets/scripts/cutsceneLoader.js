Log.info("Loaded Cutscene Class Vault");

let loader = Vars.mods.getMod("new-horizon").loader;

function loadContent(fullName){
     return loader.loadClass(fullName).newInstance(); //Garbage things
}

function loadClass(fullName){
     return loader.loadClass(fullName); //Garbage things
}

const UIActions = loadContent("newhorizon.util.feature.cutscene.UIActions");
const KeyFormat = loadContent("newhorizon.util.feature.cutscene.KeyFormat");
const WorldActions = loadContent("newhorizon.util.feature.cutscene.WorldActions");

const CutsceneEventClass = loadClass("newhorizon.util.feature.cutscene.CutsceneEvent");
const CutsceneEvent = CutsceneEventClass.newInstance();

const CutsceneEventEntity = loadContent("newhorizon.util.feature.cutscene.CutsceneEventEntity");
const CutsceneScript = loadContent("newhorizon.util.feature.cutscene.CutsceneScript");
const CCS_Scripts = CutsceneScript.scripts;
const EventSamples = loadContent("newhorizon.util.feature.cutscene.EventSamples");

const NHBlocks = loadContent("newhorizon.content.NHBlocks");
const NHBullets = loadContent("newhorizon.content.NHBullets");
const NHItems = loadContent("newhorizon.content.NHItems");
const NHLiquids = loadContent("newhorizon.content.NHLiquids");
const NHSounds = loadContent("newhorizon.content.NHSounds");
const NHWeathers = loadContent("newhorizon.content.NHWeathers");
const NHUnitTypes = loadContent("newhorizon.content.NHUnitTypes");
const NHStatusEffects = loadContent("newhorizon.content.NHStatusEffects");
const NHSectorPresets = loadContent("newhorizon.content.NHSectorPresets");
const NHFx = loadContent("newhorizon.content.NHFx");
const NHColor = loadContent("newhorizon.content.NHColor");
const NHPlanets = loadContent("newhorizon.content.NHPlanets");
const NHFunc = loadContent("newhorizon.util.func.NHFunc");
const DrawFunc = loadContent("newhorizon.util.func.DrawFunc");
const Tables = loadContent("newhorizon.util.ui.Tables");
const TableFunc = loadContent("newhorizon.util.ui.TableFunc");
const NHInterp = loadContent("newhorizon.util.func.NHInterp");
const PosLightning = loadContent("newhorizon.util.feature.PosLightning");

const FleetEventClass = loadClass("newhorizon.util.feature.cutscene.events.FleetEvent");
const ObjectiveEventClass = loadClass("newhorizon.util.feature.cutscene.events.ObjectiveEvent");
const RaidEventClass = loadClass("newhorizon.util.feature.cutscene.events.RaidEvent");
const SignalEventClass = loadClass("newhorizon.util.feature.cutscene.events.SignalEvent");
const DestroyObjectiveEventClass = loadClass("newhorizon.util.feature.cutscene.events.DestroyObjectiveEvent");

const OFFSET = 12;
const LEN = 60;

const state = Vars.state;
const tilesize = Vars.tilesize;
const world = Vars.world;

function newEvent(name, args){
    return extend(CutsceneEventClass, name, args);
}