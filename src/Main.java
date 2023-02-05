/*
Poniżej przekazujemy zadanie z prośbą o analizę poniższego kodu i zaimplementowanie
metod findBlockByColor, findBlocksByMaterial, count w klasie Wall -
najchętniej unikając powielania kodu i umieszczając całą logikę w klasie Wall.
Z uwzględnieniem w analizie i implementacji interfejsu CompositeBlock!
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

    }
    interface Structure {
        // zwraca dowolny element o podanym kolorze
        Optional<Block> findBlockByColor(String color);

        // zwraca wszystkie elementy z danego materiału
        List<Block> findBlocksByMaterial(String material);

        //zwraca liczbę wszystkich elementów tworzących strukturę
        int count();
    }

    interface Block {
        String getColor();

        String getMaterial();
    }

    interface CompositeBlock extends Block {
        List<Block> getBlocks();

    }

    public class Wall implements Structure {
        private List<Block> blocks;

        public Wall(List<Block> blocks) {
            this.blocks = blocks;
        }

        @Override
        public Optional<Block> findBlockByColor(String color) {
            for (Block block : blocks) {
                if (block.getColor().equals(color)) {
                    return Optional.of(block);
                }

                if (block instanceof CompositeBlock) {
                    Optional<Block> result = findBlockByColor((CompositeBlock) block, color);
                    if (result.isPresent()) {
                        return result;
                    }
                }
            }
            return Optional.empty();
        }

        private Optional<Block> findBlockByColor(CompositeBlock compositeBlock, String color) {
            for (Block block : compositeBlock.getBlocks()) {
                if (block.getColor().equals(color)) {
                    return Optional.of(block);
                }

                if (block instanceof CompositeBlock) {
                    Optional<Block> result = findBlockByColor((CompositeBlock) block, color);
                    if (result.isPresent()) {
                        return result;
                    }
                }
            }
            return Optional.empty();
        }

        @Override
        public List<Block> findBlocksByMaterial(String material) {
            List<Block> result = new ArrayList<>();
            for (Block block : blocks) {
                if (block.getMaterial().equals(material)) {
                    result.add(block);
                }

                if (block instanceof CompositeBlock) {
                    result.addAll(findBlocksByMaterial((CompositeBlock) block, material));
                }
            }
            return result;
        }

        private List<Block> findBlocksByMaterial(CompositeBlock compositeBlock, String material) {
            List<Block> result = new ArrayList<>();
            for (Block block : compositeBlock.getBlocks()) {
                if (block.getMaterial().equals(material)) {
                    result.add(block);
                }

                if (block instanceof CompositeBlock) {
                    result.addAll(findBlocksByMaterial((CompositeBlock) block, material));
                }
            }
            return result;
        }

        @Override
        public int count() {
            int count = 0;
            for (Block block : blocks) {
                count++;
                if (block instanceof CompositeBlock) {
                    count += count((CompositeBlock) block);
                }
            }
            return count;
        }

        private int count(CompositeBlock compositeBlock) {
            int count = 0;
            for (Block block : compositeBlock.getBlocks()) {
                count++;
                if (block instanceof CompositeBlock) {
                    count += count((CompositeBlock) block);
                }
            }
            return count;
        }
    }
}
