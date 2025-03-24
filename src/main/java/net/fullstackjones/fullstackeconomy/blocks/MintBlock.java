package net.fullstackjones.fullstackeconomy.blocks;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MintBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public MintBlock() {
        super(BlockBehaviour.Properties.of().strength(2.5F).sound(SoundType.METAL).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        switch (direction) {
            case NORTH:
                return makeShape();
            case SOUTH:
                return rotateShape(makeShape(), Direction.SOUTH);
            case WEST:
                return rotateShape(makeShape(), Direction.WEST);
            case EAST:
                return rotateShape(makeShape(), Direction.EAST);
            default:
                return makeShape();
        }
    }

    private VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Block.box(0, 0, 0, 16, 16, 16);

        return shape;
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
}
