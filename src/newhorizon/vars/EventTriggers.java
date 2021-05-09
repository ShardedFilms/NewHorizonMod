package newhorizon.vars;

import arc.Events;
import arc.func.Cons2;
import arc.struct.IntSeq;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Tile;
import newhorizon.content.NHStatusEffects;
import newhorizon.func.NHSetting;
import newhorizon.interfaces.BeforeLoadc;
import newhorizon.interfaces.ServerInitc;


public class EventTriggers{
	public static final ObjectMap<Class<?>, Seq<Cons2<? extends Building, Tile>>> onTapActor = new ObjectMap<>();
	
	public static <E extends Building> void addActor(Class<E> type, Cons2<E, Tile> act){
		Seq<Cons2<? extends Building, Tile>> actions = onTapActor.get(type);
		if(actions == null){
			actions = new Seq<>();
			actions.add(act);
			onTapActor.put(type, actions);
		}else actions.add(act);
	}
	
	public static void load(){
		Events.on(EventType.WorldLoadEvent.class, e -> {
			NHWorldVars.clear();
			NHCtrlVars.reset();
			
			for(Tile tile : Vars.world.tiles)NHWorldVars.intercepted.put(tile, new IntSeq(new int[Team.all.length]));
			
			for(BeforeLoadc c : NHWorldVars.advancedLoad){
				c.beforeLoad();
			}
			
			NHWorldVars.clearLast();
			NHWorldVars.worldLoaded = true;
		});
		
		Events.on(EventType.ClientPreConnectEvent.class, e -> {
			NHSetting.log("Server Preload Run");
			for(ServerInitc c : NHWorldVars.serverLoad){
				c.loadAfterConnect();
			}
		});
		
		Events.on(EventType.UnitChangeEvent.class, e -> {
			e.unit.apply(NHStatusEffects.invincible, 180f);
		});
		
		Events.on(EventType.TapEvent.class, e -> {
			if(Vars.headless)return;
			Building selecting = Vars.control.input.frag.config.getSelectedTile();
			if(selecting != null)for(Class<?> type : onTapActor.keys()){
				if(type == selecting.getClass()){
					for(Cons2 actor : onTapActor.get(type)){
						actor.get(selecting, e.tile);
					}
				}
			}
		});
		
//		Events.on(EventType.StateChangeEvent.class, e -> {
//			NHSetting.log("Event", "Server Preload Run");
//
//			if(NHWorldVars.worldLoaded){
//				NHSetting.log("Event", "Leaving World");
//				NHWorldVars.worldLoaded= false;
//			}
//		});
	}
}
