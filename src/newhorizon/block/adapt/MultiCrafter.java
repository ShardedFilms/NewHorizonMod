package newhorizon.block.adapt;

import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.ui.ItemImage;
import mindustry.ui.ReqImage;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.BlockStatus;
import mindustry.world.meta.Stat;

public class MultiCrafter extends GenericCrafter{
	public ObjectMap<ItemStack, Integer> exchangeMap = new ObjectMap<>();
	
	public MultiCrafter(String name){
		super(name);
	}
	
	public void setOutput(Item item){
		outputItem = new ItemStack(item, 0);
	}
	
	public void setExchangeMap(Object... items){
		for(int i = 0; i < items.length; i += 3){
			exchangeMap.put(new ItemStack((Item)items[i], ((Number)items[i + 1]).intValue()), ((Number)items[i + 2]).intValue());
		}
	}
	
	public Table exchangeTable(Building building){
		int index = 0;
		Table table = new Table();
		
		for(ItemStack stack : exchangeMap.keys()){
			table.table(i -> {
				i.add(new ReqImage(
						new ItemImage(stack.item.uiIcon, stack.amount),
						() -> building == null || building.items != null && building.items.has(stack.item, stack.amount)
				)).growX().height(40f).left();
				i.add(" -> ").growX().height(40f);
				i.add(new ItemImage(outputItem.item.uiIcon, exchangeMap.get(stack))).growX().height(40f).right();
			}).grow();
			if((++index % 2) == 0)table.row();
		}
		return table;
	}
	
	@Override
	public void setStats(){
		super.setStats();
		stats.remove(Stat.output);
		stats.add(Stat.output, t -> t.add(exchangeTable(null)));
	}
	
	@Override
	public void init(){
		Seq<ItemStack> stacks = new Seq<>(exchangeMap.size);
		for(ItemStack stack : exchangeMap.keys())stacks.add(stack);
		consumes.items(stacks.toArray(ItemStack.class));
//		for(ItemStack stack : consumes.getItem().items){
//			Log.info(stack);
//		}
		
		consumes.init();
		super.init();
	}
	
	
	
	public class MultiCrafterBuild extends GenericCrafterBuild{
		
		@Override
		public BlockStatus status(){
			if(!shouldConsume()){
				return BlockStatus.noOutput;
			}
			
			if(!isValid() || !productionValid() || count() < 1){
				return BlockStatus.noInput;
			}
			
			return BlockStatus.active;
		}
		
		@Override
		public boolean acceptItem(Building source, Item item){
			return consumes.itemFilters.get(item.id) && items.get(item) < getMaximumAccepted(item);
		}
		
		public int count(){
			int out = 0;
			for(ItemStack stack : consumes.getItem().items){
				if(items.has(stack.item, stack.amount))out += exchangeMap.get(stack);
			}
			return out;
		}
		
		@Override
		public void updateTile(){
			if(consValid()){
				progress += getProgressIncrease(craftTime);
				totalProgress += delta();
				warmup = Mathf.approachDelta(warmup, 1f, warmupSpeed);
				
				if(Mathf.chanceDelta(updateEffectChance)){
					updateEffect.at(getX() + Mathf.range(size * 4f), getY() + Mathf.range(size * 4));
				}
			}else{
				warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
			}
			
			if(progress >= 1f){
				consume();
				int out = count();
				for(int i = 0; i < out; i++){
					offload(outputItem.item);
				}
				progress %= 1;
			}
			
			if(outputItems != null && timer(timerDump, dumpTime / timeScale)){
				for(ItemStack output : outputItems){
					dump(output.item);
				}
			}
		}
		
//		@Override
//		public void consume(){
//			for(ItemStack stack : exchangeMap.keys()){
//				items.remove(stack);
//			}
//		}
		
		@Override
		public void displayBars(Table table){
			super.displayBars(table);
//			table.add(exchangeTable(this)).growX().fillY().padTop(OFFSET * 2).padBottom(OFFSET * 2).row();
//			table.table(t -> consumes.getItem().build(this, t.left())).left().grow();
		}
		
		@Override
		public boolean consValid(){
			return enabled && count() > 0 && shouldConsume();
		}
		
		@Override
		public boolean shouldConsume(){
			if(outputItems != null){
				int out = count();
				for(ItemStack output : outputItems){
					if(items.get(output.item) + count() > itemCapacity){
						return false;
					}
				}
			}
			return (outputLiquid == null || !(liquids.get(outputLiquid.liquid) >= liquidCapacity - 0.001f)) && enabled;
		}
	}
}
