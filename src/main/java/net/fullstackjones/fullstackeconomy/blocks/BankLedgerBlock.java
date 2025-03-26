package net.fullstackjones.fullstackeconomy.blocks;

import com.mojang.serialization.MapCodec;
import net.fullstackjones.fullstackeconomy.blockentities.BankLedgerBlockEntity;
import net.fullstackjones.fullstackeconomy.menu.BankLedgerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class BankLedgerBlock  extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, EntityBlock {
    public static final EnumProperty<ChestType> PART = BlockStateProperties.CHEST_TYPE;

    public static final VoxelShape SHAPE_TABLETOP = Block.box(0, 13, 0, 16, 16, 16);
    public static final VoxelShape SHAPE_LEG_1 = Block.box(6, 2, 0, 10, 13, 3);
    public static final VoxelShape SHAPE_FOOT_1 = Block.box(0, 0, 0, 16, 2, 3);
    public static final VoxelShape SHAPE_GROUP_LEG_1 = Shapes.or(SHAPE_LEG_1, SHAPE_FOOT_1);
    public static final VoxelShape SHAPE_SUPPORT_1 = Block.box(2, 0, 3, 14, 14, 16);

    public static final VoxelShape SHAPE_LEG_2 = Block.box(6, 2, 13, 10, 13, 16);
    public static final VoxelShape SHAPE_FOOT_2 = Block.box(0, 0, 13, 16, 2, 16);
    public static final VoxelShape SHAPE_GROUP_LEG_2 = Shapes.or(SHAPE_LEG_2, SHAPE_FOOT_2);
    public static final VoxelShape SHAPE_SUPPORT_2 = Block.box(2, 0, 0, 13, 14, 13);

    public static final VoxelShape SHAPE_LEGS_WEST = Shapes.or(SHAPE_GROUP_LEG_2, SHAPE_TABLETOP, SHAPE_SUPPORT_2);
    public static final VoxelShape SHAPE_LEGS_EAST = Shapes.or(SHAPE_GROUP_LEG_1, SHAPE_TABLETOP, SHAPE_SUPPORT_1);


    public BankLedgerBlock() {
        super(BlockBehaviour.Properties.of().strength(2.5F).sound(SoundType.WOOD).noOcclusion());
        this.registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof BankLedgerBlockEntity bankLedger) {
            if (player instanceof ServerPlayer) {
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(bankLedger, Component.literal("Bank Ledger")), pos);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BankLedgerBlockEntity(pos, state);
    }

    public BlockState updateShape(BlockState myState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos myPos, BlockPos pFacingPos) {
        ChestType half = myState.getValue(PART);
        BlockPos requiredNeighborPos = myPos.relative(getNeighbourDirection(half, myState.getValue(FACING)));
        boolean waterlogged = myState.getValue(WATERLOGGED);
        if (waterlogged) {
            pLevel.scheduleTick(myPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
            pLevel.scheduleTick(requiredNeighborPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        BlockState neighborState = pLevel.getBlockState(requiredNeighborPos);
        if (!neighborState.is(this)) {
            var air = (waterlogged ? Blocks.WATER : Blocks.AIR).defaultBlockState();
            pLevel.setBlock(myPos, air, 35);
            pLevel.levelEvent(null, 2001, myPos, Block.getId(air));
            return air;
        }
        return super.updateShape(myState, pFacing, pFacingState, pLevel, myPos, pFacingPos);
    }

    private static Direction getNeighbourDirection(ChestType pPart, Direction pDirection) {
        return pPart == ChestType.LEFT ? pDirection.getCounterClockWise() : pDirection.getClockWise();
    }


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(PART).equals(ChestType.RIGHT) ? pState.getValue(FACING) : pState.getValue(FACING).getOpposite();
        return switch (direction) {
            case NORTH -> rotateShape(SHAPE_LEGS_WEST, Direction.EAST);
            case SOUTH -> rotateShape(SHAPE_LEGS_WEST, Direction.WEST);
            case WEST -> SHAPE_LEGS_WEST;
            default -> SHAPE_LEGS_EAST;
        };
    }

    private VoxelShape rotateShape(VoxelShape shape, Direction direction) {
        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};

        int times = (direction.get2DDataValue() + 2) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                buffer[1] = Shapes.or(buffer[1], Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX));
            });
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }


    @javax.annotation.Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction direction = pContext.getHorizontalDirection();
        BlockPos blockpos = pContext.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(direction.getCounterClockWise());
        Level level = pContext.getLevel();
        if (level.getBlockState(blockpos1).canBeReplaced(pContext) && level.getWorldBorder().isWithinBounds(blockpos1)) {
            return this.defaultBlockState().setValue(FACING, direction.getOpposite()).setValue(WATERLOGGED, level.getFluidState(blockpos).getType() == Fluids.WATER);
        }

        return null;
    }

    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if (!pLevel.isClientSide) {
            BlockPos blockpos = pPos.relative(pState.getValue(FACING).getClockWise());
            pLevel.setBlock(blockpos, pState.setValue(PART, ChestType.LEFT).setValue(WATERLOGGED, pLevel.getFluidState(blockpos).getType() == Fluids.WATER), 3);
            pLevel.setBlock(pPos, pState.setValue(PART, ChestType.RIGHT), 3);
            pLevel.blockUpdated(pPos, Blocks.AIR);
            pState.updateNeighbourShapes(pLevel, pPos, 3);
        }

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART, WATERLOGGED);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        if (blockState.getValue(PART).equals(ChestType.RIGHT))
            return RenderShape.MODEL;
        else
            return RenderShape.INVISIBLE;
    }

    @Override
    protected FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.BLOCK;
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return null;
    }
}
